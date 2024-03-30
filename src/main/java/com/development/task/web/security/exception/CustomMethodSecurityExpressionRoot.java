package com.development.task.web.security.exception;

import com.development.task.domain.user.Role;
import com.development.task.service.UserService;
import com.development.task.web.security.JwtEntity;
import jakarta.servlet.http.HttpServletRequest;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.access.expression.SecurityExpressionRoot;
import org.springframework.security.access.expression.method.MethodSecurityExpressionOperations;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.Authentication;

@Getter
@Setter
public class CustomMethodSecurityExpressionRoot extends SecurityExpressionRoot
        implements MethodSecurityExpressionOperations {

    private Object filterObject;
    private Object returnObject;
    private Object target;
    private HttpServletRequest request;
    private UserService userService;

    public CustomMethodSecurityExpressionRoot(final Authentication authentication){
        super(authentication);
    }

    public boolean canAccessUser(final Long id){
        JwtEntity usr = (JwtEntity) this.getPrincipal();
        Long usId  = usr.getId();
        return usId.equals(id) || hasAnyRole(Role.ROLE_ADMIN);
    }

    private boolean hasAnyRole(final Role... roles){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        for(Role role: roles){
            SimpleGrantedAuthority authority = new SimpleGrantedAuthority(role.name());
            if(authentication.getAuthorities().contains(authority)){
                    return true;
            }
        }
        return false;

    }

    public boolean canAccessTask(final Long taskId){
        JwtEntity usr  = (JwtEntity) this.getPrincipal();
        Long id = usr.getId();
        return userService.isTaskOwner(id,taskId);
    }

    @Override
    public Object getThis() {
        return target;
    }
}
