package com.core.service;

import com.core.dto.oauth.UserTokenDTO;
import com.core.entities.User;
import com.core.integration.OauthIntegration;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Slf4j
@Service
public class OauthService {

    @Autowired
    private OauthIntegration oauthIntegration;

    public UserTokenDTO getUserData(String token){
        try {
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.add("Authorization", token);

            return oauthIntegration.findUserByToken(httpHeaders);
        }catch (Exception e){
            log.error(e.getMessage());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "ERROR on find user data by token: " + token);
        }
    }

    public User getUserByToken(String token) {
        UserTokenDTO userTokenDTO = this.getUserData(token);
        User user = new User();
        user.setId(userTokenDTO.getId());

        return user;
    }
}
