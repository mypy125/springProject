package com.development.task.web.dto.auth;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Schema(description = "Request from login")
public class JwtRequest {

    @NotNull(message = "username most be not null")
    private String username;

    @NotNull(message = "password most be not null")
    private String password;
}
