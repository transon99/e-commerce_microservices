package com.sondev.orderservice.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.UuidGenerator;

import java.util.Date;
import java.util.List;

@Data
@Builder
@EqualsAndHashCode(callSuper = true)
public class Orders extends AbstractMappedEntity {
    @Id
    @UuidGenerator
    @Column(name = "id", unique = true, nullable = false, updatable = false)
    private String id;

    @Column(name = "order_date")
    private Date orderDate;

    @Column(name = "total_price")
    private double totalPrice;

    @Column(name = "payment_method_id")
    private String paymentMethodId;

    @Column(name = "is_accept")
    private boolean isAccept;

    @Column(name = "status")
    private Status status;

    @Column(name = "user_id")
    private String userId;

    @Column(name = "cart_id")
    private String cartId;
}
