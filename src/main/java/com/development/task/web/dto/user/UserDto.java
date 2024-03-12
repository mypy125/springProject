package com.development.task.web.dto.user;

import com.development.task.domain.task.Task;
import com.development.task.domain.user.Role;
import com.development.task.web.dto.vaqlidation.OnCreate;
import com.development.task.web.dto.vaqlidation.OnUpdate;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.validator.constraints.Length;


import java.util.List;
import java.util.Set;
@Data
public class UserDto {
    @NotNull(message = "id most be not null", groups = OnUpdate.class)
    private Long id;

    @NotNull(message = "name most be not null", groups = {OnCreate.class, OnUpdate.class})
    @Length(max = 255, message = "name length most be smale then 255 symbols", groups = {OnCreate.class, OnUpdate.class})
    private String name;
    private String username;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @NotNull(message = "password most be not null", groups = {OnCreate.class, OnUpdate.class})
    private String password;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @NotNull(message = "password configuration most be not null", groups = {OnCreate.class})
    private String passwordConfirmation;

}
