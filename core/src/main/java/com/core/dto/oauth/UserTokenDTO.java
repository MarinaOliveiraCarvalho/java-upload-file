package com.core.dto.oauth;

import lombok.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserTokenDTO implements Serializable {

    private UUID id;
    private String name;
    private String email;
    private String password;
    private Set<String> roles = new HashSet<>();

}
