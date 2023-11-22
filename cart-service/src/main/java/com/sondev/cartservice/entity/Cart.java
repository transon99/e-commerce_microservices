package com.sondev.cartservice.entity;

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
@Document(collection = "cart")
public class Cart extends AbstractMappedEntity {

    @MongoId(FieldType.OBJECT_ID)
    private String id;

    @Field(name = "user_id")
    private String userId;

    @Field(name = "cart_items")
    private List<String> cartItems;

}
