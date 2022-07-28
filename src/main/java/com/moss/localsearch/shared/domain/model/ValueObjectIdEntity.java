package com.moss.localsearch.shared.domain.model;

import org.springframework.data.domain.Persistable;

import javax.persistence.MappedSuperclass;
import javax.persistence.PostLoad;
import javax.persistence.PostPersist;
import javax.persistence.Transient;
import java.io.Serializable;

/**
 * ValueObject를 Id로 가지는 Entity의 Base Class
 * save시 존재여부 체크를 제거
 */
@MappedSuperclass
public abstract class ValueObjectIdEntity<ID extends Serializable> implements Persistable<ID> {
    @Transient
    private boolean isNew = true;

    @Override
    public boolean isNew() {
        return isNew;
    }

    @PostPersist
    @PostLoad
    private void markNotNew() {
        this.isNew = false;
    }
}