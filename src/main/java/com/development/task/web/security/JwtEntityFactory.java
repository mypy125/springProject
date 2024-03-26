package com.development.task.web.security;

import com.development.task.domain.user.Role;
import com.development.task.domain.user.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class JwtEntityFactory {
    public static JwtEntity create(final User user){
        return new JwtEntity(user.getId(), user.getUsername(),
                user.getName(), user.getPassword(),
                mapToGrantedAuthorities(new ArrayList<>(user.getRoles())) );
    }

    private static Collection<? extends GrantedAuthority> mapToGrantedAuthorities(final List<Role> role) {
        return role.stream().map(Enum::name).map(SimpleGrantedAuthority::new).collect(Collectors.toList());
    }
}
