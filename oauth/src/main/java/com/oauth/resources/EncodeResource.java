package com.oauth.resources;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping(value = "/bcrypt")
public class EncodeResource {

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @GetMapping(value = "encode/{key}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<String> createCode(@PathVariable String key) {
        try {
            log.info("Encode key: {}", key);
            return ResponseEntity.ok().body(bCryptPasswordEncoder.encode(key));
        }
        catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @GetMapping(value = "check/{key}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<String> check(@PathVariable String key) {
        try {
            log.info("check key: {}", key);
            Boolean math = bCryptPasswordEncoder.matches(key, "$2a$10$nua5gCm4KF64DrXwiG34tebfCrCK3aCreTmlqTmZYtz/pNj8/LUKO");
            return ResponseEntity.ok().body(math.toString());
        }
        catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
}
