package com.moss.localsearch.local.domain.model;


import com.moss.localsearch.shared.domain.model.ValueObjectIdEntity;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 검색 키워드
 */
@Getter
@EqualsAndHashCode(callSuper = false)
@ToString
//----
@Entity
@Table
public class Keyword extends ValueObjectIdEntity<String> {
    // 키워드
    @Id
    private String keyword;
    
    // 호출 수
    private Integer count;
    
    public Keyword(String keyword, Integer count) {
        this.keyword = keyword;
        this.count = count;
    }

    protected Keyword() {
        this.keyword = null;
        this.count = null;
    }

    /**
     * 호출 수 추가
     * @param count
     */
    public void add(Integer count) {
        this.count += count;
    }

    @Override
    public String getId() {
        return keyword;
    }
}
