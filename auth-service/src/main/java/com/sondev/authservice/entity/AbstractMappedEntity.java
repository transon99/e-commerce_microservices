package com.sondev.authservice.entity;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SourceType;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

@MappedSuperclass
@NoArgsConstructor
@AllArgsConstructor
@Data
abstract public class AbstractMappedEntity implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @LastModifiedBy
    @Column(name = "updated_by")
    private String updatedBy;

    @UpdateTimestamp(source = SourceType.DB)
    @Column(name = "updated_at")
    private Date updatedDate;

    @CreatedBy
    @Column(name = "created_by")
    private String createdBy;

    @CreationTimestamp(source = SourceType.DB)
    @Column(name = "created_at")
    private Date createdAt;
}
