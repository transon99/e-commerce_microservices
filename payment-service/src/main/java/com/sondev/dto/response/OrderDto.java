package com.sondev.dto.response;

import com.sondev.dto.AbstractDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.Date;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDto extends AbstractDto<String> {

    private String id;

    private Date orderDate;

    private double totalPrice;

    private String paymentMethodId;

    private boolean isAccept;

    private String status;

    private String userId;

    private String cartId;

}