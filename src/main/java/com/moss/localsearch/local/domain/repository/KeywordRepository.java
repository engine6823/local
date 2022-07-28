package com.moss.localsearch.local.domain.repository;

import com.moss.localsearch.local.domain.model.Keyword;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.persistence.LockModeType;
import java.util.List;
import java.util.Optional;

/**
 * 키워드 Repository
 */
@Repository
public interface KeywordRepository extends JpaRepository<Keyword, String> {

    /**
     * 키워드 조회 (For Update)
     */
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT k FROM Keyword k WHERE k.keyword = :keyword")
    Optional<Keyword> findByIdForUpdate(String keyword);

    /**
     * 키워드 목록 조회 - 검색 횟수 역순 10개 조회
     * @return 키워드 목록
     */
    List<Keyword> findTop10ByOrderByCountDesc();
}
