package com.ernestocesario.myclothes.configurations.security;

import com.ernestocesario.myclothes.configurations.exceptionHandlers.FilterExceptionHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final FilterExceptionHandler filterExceptionHandler;

    @Value("${testing}")
    private boolean testing;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable);

        if (testing)
            http
                    .authorizeHttpRequests((authorize) -> authorize
                            .anyRequest().permitAll()
                    );
        else
            http
                    .authorizeHttpRequests((authorize) -> authorize
                                .requestMatchers("/api/v1/auth/login").permitAll()
                                .anyRequest().authenticated()
                    );


        http
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        http
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(filterExceptionHandler, JwtAuthenticationFilter.class);

        return http.build();
    }
}
