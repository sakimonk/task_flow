package com.tf.task.flow.api.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * @author ouweijian
 * @date 2025/3/12 13:51
 */
@EnableMethodSecurity
@Configuration
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    private final String[] permitUrlArr = new String[]{
            // swagger
            "/v3/api-docs/**",
            "/swagger-ui/**",
            "/swagger-ui.html",
            // business api
            "/user/register",
            "/user/login",
            // TODO ###IMPORTANT### if didn't add this statement, spring security's AnonymousAuthenticationFilter would prevent to response the long polling result
            // maybe the only way to figure it out is to view the Spring Security's source code
            "/todoModification/**"
    };

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, JwtAuthenticationFilter jwtAuthenticationFilter) throws Exception {
        http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(permitUrlArr).permitAll()
                        .anyRequest().authenticated()
                )
                // Use Jwt token, no need to use session
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .csrf(AbstractHttpConfigurer::disable) // Close CSRF protection（optional）
                .httpBasic(AbstractHttpConfigurer::disable) // Close Basic authentication（optional）
                .formLogin(AbstractHttpConfigurer::disable) // close form login（optional）
                ;


        return http.build();
    }

    // authenticationManager
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

}
