package com.development.task.web.security.exception;


import com.development.task.domain.user.Role;
import com.development.task.service.UserService;
import com.development.task.web.security.JwtEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;


@Component("cse")
@RequiredArgsConstructor
public class CustomSecurityExpression {
    private final UserService userService;

    public boolean canAccessUser(final Long id){
        JwtEntity usr = getPrincipal();
        Long uId = usr.getId();
        return uId.equals(id) || hasAnyRole(Role.ROLE_USER);
    }

    private boolean  hasAnyRole(Role... roles){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        for(Role rol: roles){
            SimpleGrantedAuthority authority = new SimpleGrantedAuthority(rol.name());
            if(authentication.getAuthorities().contains(authority)){
                return true;
            }
        }
        return false;
    }

    public boolean canAccessTask(final Long tId){
        JwtEntity usr = getPrincipal();
        Long uId = usr.getId();
        return userService.isTaskOwner(uId,tId);
    }

    private JwtEntity getPrincipal() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return (JwtEntity) authentication.getPrincipal();
    }

}
