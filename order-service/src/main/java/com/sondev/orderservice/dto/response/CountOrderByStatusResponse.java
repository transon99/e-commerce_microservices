package com.sondev.orderservice.dto.response;

import com.sondev.orderservice.entity.Status;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CountOrderByStatusResponse {

    private Status status;
    private Integer number;

}
