package com.core.integration;

import com.core.dto.oauth.UserTokenDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@Component
@FeignClient(name = "oauth", path = "/users")
public interface OauthIntegration {

    @GetMapping(value = "/token")
    UserTokenDTO findUserByToken(@RequestHeader HttpHeaders headers);
}
