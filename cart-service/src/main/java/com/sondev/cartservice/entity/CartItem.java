package com.sondev.cartservice.entity;

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
@Document(collection = "cartItem")
public class CartItem extends AbstractMappedEntity {

    @MongoId(FieldType.OBJECT_ID)
    private String id;

    @Field(name = "quantity")
    private Integer quantity;

    @Field(name = "product_id")
    private String productId;

    @Field(name = "user_id")
    private String userId;

    @Field(name = "order_id")
    private String orderId;
}
