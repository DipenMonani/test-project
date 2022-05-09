package com.testtask.common.entity.auditable;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseRepoEntityAuditable<U, PK extends Serializable> extends BaseRepoEntity<PK> {
    private static final long serialVersionUID = 141481953116476081L;
    @CreatedBy
    @Column(updatable = false)
    private U createdBy;

    @Temporal(TemporalType.TIMESTAMP)
    @CreatedDate
    @Column(updatable = false)
    private Date createdDate;

    @LastModifiedBy
    private U lastModifiedBy;

    @Temporal(TemporalType.TIMESTAMP)
    @LastModifiedDate
    private Date lastModifiedDate;

    public BaseRepoEntityAuditable() {
    }
}