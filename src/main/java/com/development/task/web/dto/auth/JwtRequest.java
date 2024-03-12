package com.development.task.web.dto.auth;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class JwtRequest {

    @NotNull(message = "username most be not null")
    private String username;

    @NotNull(message = "password most be not null")
    private String password;
}
