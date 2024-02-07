package com.sondev.orderservice.dto.request;

import com.sondev.orderservice.entity.AbstractMappedEntity;
import com.sondev.orderservice.entity.Order;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UuidGenerator;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class OrderItemRequest extends AbstractMappedEntity {

    private Integer quantity;

    private String productId;

    private String userId;

}
