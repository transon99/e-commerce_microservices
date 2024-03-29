package com.sondev.productservice.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SourceType;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

@MappedSuperclass
@NoArgsConstructor
@AllArgsConstructor
@Data
@EntityListeners(AuditingEntityListener.class)
@JsonIgnoreProperties(value = { "createdBy", "createdAt", "lastModifiedBy", "lastModifiedAt" }, allowGetters = true)
public abstract class AbstractMappedEntity<T> implements Serializable {

    public abstract T getId();

    @Serial
    private static final long serialVersionUID = 1L;

    @LastModifiedBy
    @Column(name = "last_modified_by", length = 50)
    private String lastModifiedBy;

    @UpdateTimestamp(source = SourceType.DB)
    @Column(name = "updated_at")
    @DateTimeFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    private LocalDateTime lastModifiedAt;

    @CreatedBy
    @Column(name = "created_by", length = 50, updatable = false)
    private String createdBy;

    @CreationTimestamp(source = SourceType.DB)
    @DateTimeFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    @Column(name = "created_at")
    private LocalDateTime createdAt;

}
