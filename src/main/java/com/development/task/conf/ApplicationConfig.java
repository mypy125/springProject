package com.development.task.conf;

import com.development.task.web.security.JwtTokenFilter;
import io.minio.MinioClient;
import com.development.task.service.props.MinioProperties;
import com.development.task.web.security.JwtTokenProvider;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import lombok.SneakyThrows;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Lazy;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor(onConstructor =  @__(@Lazy))
public class ApplicationConfig {
    private final JwtTokenProvider tokenProvider;
    private final ApplicationContext applicationContext;
    private final MinioProperties minioProperties;

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    @SneakyThrows
    public AuthenticationManager authenticationManager(
            final AuthenticationConfiguration configuration){
       return configuration.getAuthenticationManager();
    }

    @Bean
    public MinioClient minioClient(){
        return MinioClient.builder().endpoint(minioProperties.getUrl())
                .credentials(minioProperties.getAccessKey(),minioProperties.getSecretKey()).build();
    }

    @Bean
    public OpenAPI openAPI(){
        return new OpenAPI().addSecurityItem(new SecurityRequirement()
                        .addList("bearerAuth")).components(new Components()
                                .addSecuritySchemes("bearerAuth", new SecurityScheme()
                                                .type(SecurityScheme.Type.HTTP)
                                                .scheme("bearer")
                                                .bearerFormat("JWT")))
                .info(new Info()
                        .title("Task list API")
                        .description("Demo Spring Boot application")
                        .version("1.0"));
    }

    @Bean
    @SneakyThrows
    public SecurityFilterChain securityFilterChain(final HttpSecurity httpSecurity){
        httpSecurity.csrf(AbstractHttpConfigurer::disable).cors(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable).sessionManagement(sessionManagement -> {
                    sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
                }).exceptionHandling(configurer -> configurer.authenticationEntryPoint((request, response, authException) ->{
                    response.setStatus(HttpStatus.FORBIDDEN.value());
                    response.getWriter().write("Unauthorized.");
                } ))
                .authorizeHttpRequests(configurer -> configurer.requestMatchers("/api/v1/auth/**")
                        .permitAll().requestMatchers("/swagger-ui/**")
                        .permitAll().requestMatchers("/v3/api-docs/**")
                        .permitAll().requestMatchers("/graphiql")
                        .permitAll().anyRequest().authenticated())
                .anonymous(AbstractHttpConfigurer::disable).addFilterBefore(new JwtTokenFilter(tokenProvider), UsernamePasswordAuthenticationFilter.class);
        return httpSecurity.build();

    }


}
