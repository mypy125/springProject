package com.development.task.web.dto.user;

import com.development.task.domain.task.Task;
import com.development.task.domain.user.Role;
import com.development.task.web.dto.vaqlidation.OnCreate;
import com.development.task.web.dto.vaqlidation.OnUpdate;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
@Schema(description = "User Dto")
public class UserDto {
    @Schema(description = "User id")
    @NotNull(message = "id most be not null", groups = OnUpdate.class)
    private Long id;

    @Schema(description = "User name", example = "grigory")
    @NotNull(message = "name most be not null", groups = {OnCreate.class, OnUpdate.class})
    @Length(max = 255, message = "name length most be smale then 255 symbols", groups = {OnCreate.class, OnUpdate.class})
    private String name;

    @Schema(description = "user email", example = "grigory@gmail.com")
    @NotNull(message = "Username most be not null", groups = {OnCreate.class, OnUpdate.class})
    @Length(max = 255, message = "Username length most be smaller 255 symbols. ")
    private String username;

    @Schema(description = "User encrypted password ")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @NotNull(message = "password most be not null", groups = {OnCreate.class, OnUpdate.class})
    private String password;

    @Schema(description = "User password confirmation ")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @NotNull(message = "password configuration most be not null", groups = {OnCreate.class})
    private String passwordConfirmation;

}
