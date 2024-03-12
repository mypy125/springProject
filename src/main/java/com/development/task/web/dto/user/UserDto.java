package com.development.task.web.dto.user;

import com.development.task.domain.task.Task;
import com.development.task.domain.user.Role;
import lombok.Data;

import java.util.List;
import java.util.Set;
@Data
public class UserDto {
    private Long id;
    private String name;
    private String username;
    private String password;
    private String passwordConfirmation;
    private Set<Role> roles;
    private List<Task> tasks;
}
