package com.sondev.orderservice.dto.response;

import com.sondev.orderservice.dto.AbstractDto;
import com.sondev.orderservice.entity.Order;
import jakarta.persistence.Column;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderItemDto extends AbstractDto<String> {
    private String id;

    private Integer quantity;

    private String productId;

    private String userId;

}
