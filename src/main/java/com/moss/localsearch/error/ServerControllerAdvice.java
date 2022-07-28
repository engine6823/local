package com.moss.localsearch.error;

import com.moss.localsearch.shared.domain.code.SharedErrorCode;
import com.moss.localsearch.shared.error.BusinessException;
import com.moss.localsearch.shared.error.ErrorCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;
import java.net.BindException;
import java.util.Map;

/**
 * 공통 에러 처리를 위한 ControllerAdvice
 */
@Slf4j
@RestControllerAdvice
public class ServerControllerAdvice {

    /**
     * Validation Exception을 처리한다.<br />
     * MethodParameter: ConstraintViolationException.
     * MethodArgumentNotValidException: @RequestBody ModelBinding ValidationException
     * MissingServletRequestParameterException: @PathVariable 지정된 parameter에 값이 없는 경우
     * BindException: @ModelAttribute ModelBinding ValidationException
     */
    @ExceptionHandler({
            ConstraintViolationException.class,
            MethodArgumentNotValidException.class,
            BindException.class,
            MissingServletRequestParameterException.class})
    public ErrorResult<Object> validationException(Exception ex, HttpServletRequest request, HttpServletResponse response) {

        // 기본처리
        ErrorResult<Object> result = new ErrorResult<>();
        result.setError(SharedErrorCode.InvalidParam);
        result.setText(ex.getMessage());

        // 후처리
        postProcess(result.getError(), response);
        log.warn("Bad Request", ex.getMessage());

        return result;
    }

    /**
     * BusinessException 처리
     */
    @ExceptionHandler({ BusinessException.class })
    public ErrorResult<Map<String, Object>> businessException(BusinessException ex, HttpServletRequest request, HttpServletResponse response) {
        // 결과생성
        ErrorResult<Map<String, Object>> result = new ErrorResult<>();
        result.setError(ex.getErrorCode());
        result.setText(ex.getErrorCode().name());

        // 후처리
        postProcess(result.getError(), response);
        log.warn("Business Exception", ex.getErrorCode().name());

        return result;
    }

    /**
     * Page Not Found(404) 에러 처리<br />
     * Spring + Tomcat: WebInitializer의 Servlet 설정시 InitParameter로 [throwExceptionIfNoHandlerFound, true] 지정 필요
     * <pre>{@code
     *     protected void registerDispatcherServlet(ServletContext servletContext) {
     *         ...
     *         ServletRegistration.Dynamic registration =
     *              servletContext.addServlet("main", dispatcherServlet);
     *         registration.setInitParameter("throwExceptionIfNoHandlerFound", "true");
     *         ...
     *     }
     * }</pre>
     *
     * Spring Boot + Embedded Tomcat: spring.mvc.throw-exception-if-no-handler-found=true 설정 필요 <br />
     * 다만 해당 설정은 하지 않고, ErrorController에서 NoHandlerFoundException을 구성 후 이 Method를 호출하여서 처리
     * @see <a href="https://stackoverflow.com/questions/22157687/spring-mvc-rest-handing-bad-url-404-by-returning-json">Spring MVC REST Handing Bad Url (404) by returning JSON</a>
     */
    @ExceptionHandler({ NoHandlerFoundException.class })
    public ErrorResult<Object> noHandlerFoundException(NoHandlerFoundException ex, HttpServletRequest request, HttpServletResponse response) {
        // 결과생성
        ErrorResult<Object> result = new ErrorResult<>();
        result.setError(SharedErrorCode.PageNotFound);
        if (StringUtils.hasText(ex.getMessage())) {
            result.setText(ex.getMessage());
        } else {
            result.setText("System Error");
        }

        // 후처리
        postProcess(result.getError(), response);

        return result;
    }

    /**
     * 처리되지 않은 Exception 처리
     * StatusCode 500 반환
     */
    @ExceptionHandler({ RuntimeException.class, Exception.class })
    public ErrorResult<Map<String, Object>> exception(Exception ex, HttpServletRequest request, HttpServletResponse response) {
        // 결과 생성
        ErrorResult<Map<String, Object>> result = new ErrorResult<>();
        result.setError(SharedErrorCode.SystemError);
        result.setText(ex.getMessage());

        // 후처리
        postProcess(result.getError(), response);
        log.error("Unhandled Exception", ex);

        return result;
    }

    /**
     * 공통 후처리<br />
     * ErrorCode에 대한 Response의 StatusCode를 설정
     */
    protected void postProcess(ErrorCode<?> errorCode, HttpServletResponse response) {
        if (log.isDebugEnabled()) {
            log.debug("=== postProcess, errorCode={}", errorCode);
        }
        response.setStatus(errorCode.httpStatus());
    }
}
