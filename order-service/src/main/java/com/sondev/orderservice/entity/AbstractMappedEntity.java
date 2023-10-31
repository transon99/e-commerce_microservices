package com.sondev.orderservice.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Date;

@Getter
@Setter
public class AbstractMappedEntity{
    @LastModifiedBy
    @Field(name = "updated_by")
    private String updatedBy;

    @LastModifiedDate
    @Field(name = "updated_at")
    private Date updatedDate;

    @CreatedBy
    @Field(name = "created_by")
    private String createdBy;

    @CreatedDate
    @Field(name = "created_at")
    private Date createdAt;
}
