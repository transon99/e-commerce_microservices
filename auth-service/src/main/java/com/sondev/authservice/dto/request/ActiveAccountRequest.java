package com.sondev.authservice.dto.request;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class ActiveAccountRequest {
    @NotEmpty
    private String token;

    @NotEmpty
    private String userId;
}
