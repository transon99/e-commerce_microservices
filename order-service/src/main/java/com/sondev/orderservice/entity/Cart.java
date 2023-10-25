package com.sondev.orderservice.entity;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;
import org.springframework.data.mongodb.core.mapping.MongoId;

@Data
@Builder
@EqualsAndHashCode(callSuper = true)
@Document(collection = "order_details")
public class Cart extends AbstractMappedEntity {

    @MongoId(FieldType.OBJECT_ID)
    private String id;

    @Field(name = "price")
    private Double price;

    @Field(name = "quantity")
    private Integer quantity;

    @Field(name = "product_id")
    private String productId;

}
