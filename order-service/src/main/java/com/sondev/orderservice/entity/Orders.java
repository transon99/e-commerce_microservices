package com.sondev.orderservice.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.UuidGenerator;

import java.util.List;

@Data
@Builder
@EqualsAndHashCode(callSuper = true)
public class Orders extends AbstractMappedEntity {
    @Id
    @UuidGenerator
    @Column(name = "id", unique = true, nullable = false, updatable = false)
    private String id;

    @Column(name = "first_name", nullable = false, columnDefinition = "char(255)")
    private String firstName;

    @Column(name = "last_name", nullable = false, columnDefinition = "char(255)")
    private String lastName;

    @Column(name = "email", nullable = false, columnDefinition = "char(255)")
    @Email(message = "*Input must be in Email format!**")
    private String email;

    @Column(name = "phone_number", nullable = false, columnDefinition = "char(255)")
    private String phoneNumber;

    @Column(name = "status")
    private Integer status;

    @Column(name = "user_id")
    private String userId;

    @Column(name = "cart_ids")
    private String cartId;
}
