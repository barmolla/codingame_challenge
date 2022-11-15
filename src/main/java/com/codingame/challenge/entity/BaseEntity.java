package com.codingame.challenge.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

@MappedSuperclass
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class BaseEntity implements Serializable {

	private static final long serialVersionUID = 649400904691035967L;

	@Column(name = "created_at")
    @CreatedDate
    private Date createdAt;

    @Column(name = "updated_at")
    @LastModifiedDate
    private Date updatedAt;

    @Column
    private boolean deleted = false;

	@PrePersist
    protected void prePersist() {
        if (this.createdAt == null) createdAt = new Date();
    }

    @PreUpdate
    protected void preUpdate() {
        this.updatedAt = new Date();
    }

	public boolean isDeleted() {
		return deleted;
	}

	public boolean isNotDeleted() {
		return !deleted;
	}

	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}
}
