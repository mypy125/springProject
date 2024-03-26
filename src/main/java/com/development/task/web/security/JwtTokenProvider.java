package com.development.task.web.security;

import com.development.task.domain.exception.AccessDeniedException;
import com.development.task.domain.user.Role;
import com.development.task.domain.user.User;
import com.development.task.service.UserService;
import com.development.task.service.props.JwtProperties;
import com.development.task.web.dto.auth.JwtResponse;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Set;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class JwtTokenProvider {
    private final JwtProperties jwtProperties;

    private final UserDetailsService userDetailsService;
    private final UserService userService;
    private SecretKey key;

    @PostConstruct
    public void init(){
        this.key = Keys.hmacShaKeyFor(jwtProperties.getSecret().getBytes());
    }

    public String createAccessToken(final Long userId, final String userName,
                                    final Set<Role> role){
        Claims claims = Jwts.claims().subject(userName).add("id",userId)
                .add("roles", resolveRoles(role)).build();
        Instant validity = Instant.now().plus(jwtProperties.getAccess(), ChronoUnit.HOURS);
        return Jwts.builder().claims(claims).expiration(Date.from(validity)).signWith(key).compact();
    }

    private Object resolveRoles(Set<Role> role) {
        return role.stream().map(Enum::name).collect(Collectors.toList());
    }

    public String createRefreshToken(final Long userId,final String username){
        Claims claims = Jwts.claims().subject(username).add("id",userId).build();
        Instant validity  = Instant.now().plus(jwtProperties.getRefresh(), ChronoUnit.DAYS);
        return Jwts.builder().claims(claims).expiration(Date.from(validity)).signWith(key).compact();
    }

    public JwtResponse refreshUserToken(final String refreshToken){
        JwtResponse jwtResponse = new JwtResponse();
        if(isValid(refreshToken)){
            throw new AccessDeniedException();
        }
        Long userid = Long.valueOf(getId(refreshToken));
        User user = userService.getById(userid);
        jwtResponse.setId(userid);
        jwtResponse.setUsername(user.getUsername());
        jwtResponse.setAccessToken(createAccessToken(userid,user.getUsername(),user.getRoles()));
        jwtResponse.setRefreshToken(createRefreshToken(userid, user.getUsername()));
        return jwtResponse;
    }

    public boolean isValid(final String token){
        Jws<Claims> claimsJws = Jwts.parser().verifyWith(key).build().parseSignedClaims(token);
        return claimsJws.getPayload().getExpiration().after(new Date());
    }

    private String getId(final String token){
        return Jwts.parser().verifyWith(key).build().parseSignedClaims(token).getPayload().getSubject();
    }

    private String getUsername(final String token){
        return Jwts.parser().verifyWith(key).build().parseSignedClaims(token).getPayload().getSubject();
    }
    public UsernamePasswordAuthenticationToken getAuthentication(final String token){
        String username = getUsername(token);
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        return new UsernamePasswordAuthenticationToken(userDetails,"",userDetails.getAuthorities());
    }

}
