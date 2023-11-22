package com.sondev.productservice.dto.response;


import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ResponseMessage {
    private String status;
    private String message;
    private Object data;


}
