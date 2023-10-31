package com.sondev.orderservice.entity;

import jakarta.validation.constraints.Email;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.util.List;

@Data
@Builder
@EqualsAndHashCode(callSuper = true)
@Document(collection = "orders")
public class Orders extends AbstractMappedEntity {
    @MongoId(FieldType.OBJECT_ID)
    private String id;

    @Field(name = "first_name")
    private String firstName;

    @Field(name = "last_name")
    private String lastName;

    @Field(name = "email")
    @Email(message = "*Input must be in Email format!**")
    private String email;

    @Field(name = "phone_number")
    private String phoneNumber;

    @Field(name = "status")
    private Integer status;

    @Field(name = "user_id")
    private Double userId;

    @Field(name = "order_detail_ids")
    private List<String> orderDetailIds;
}
