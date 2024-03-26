package com.development.task.conf;

import com.development.task.service.props.MinioProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationConfig {
    private final JwtTokenProvider tokenProvider;
    private final ApplicationContext applicationContext;
    private final MinioProperties minioProperties;


}
