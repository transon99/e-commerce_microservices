package com.sondev.authservice.feignclient;

import com.sondev.authservice.dto.UserZalo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "zalo-api", url = "https://graph.zalo.me/v2.0/me?fields=id,name,picture")
public interface ZaloApi {
    @GetMapping()
    UserZalo getInformation(@RequestHeader(name = "access_token", required = true) String accessToken);
}
