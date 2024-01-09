package com.sondev.productservice.feignclient;

import com.sondev.common.response.ResponseMessage;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "auth-service", url = "http://localhost:8082/api/v1/auth/user")
public interface UserClient {
    @GetMapping(value = "/{id}")
    ResponseEntity<ResponseMessage> findById(@RequestHeader(name = "Authorization", required = true) String token, @PathVariable(name = "id") String id);
}
