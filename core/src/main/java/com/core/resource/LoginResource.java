package com.core.resource;

import com.core.dto.UserDto;
import com.core.entities.User;
import com.core.service.LoginService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping(value = "/login")
public class LoginResource {

    @Autowired
    LoginService loginService;

    @PostMapping
    public ResponseEntity<User> create(@RequestBody @Valid UserDto userDto) {
        return ResponseEntity.ok(this.loginService.createNewUser(userDto));
    }
}
