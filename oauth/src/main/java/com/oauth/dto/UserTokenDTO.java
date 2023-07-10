package com.oauth.dto;

import com.oauth.entities.Role;
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

    private static final long serialVersionUID = 1L;

    private UUID id;
    private String name;
    private String email;
    private String password;
    private Set<Role> roles = new HashSet<>();

}
