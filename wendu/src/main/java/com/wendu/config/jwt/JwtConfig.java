package com.wendu.config.jwt;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author LittleDumpling
 */
@Configuration
public class JwtConfig {

    @Bean
    @ConfigurationProperties(prefix = "jwt.config")
    public JwtProperty jwt() {
        return new JwtProperty();
    }

}
