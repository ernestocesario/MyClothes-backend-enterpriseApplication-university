package com.ernestocesario.myclothes.configurations.security;

import com.ernestocesario.myclothes.persistance.entities.User;
import com.ernestocesario.myclothes.services.implementations.JwtServiceImpl;
import com.ernestocesario.myclothes.services.implementations.UserServiceImpl;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtServiceImpl jwtService;
    private final UserServiceImpl userService;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String jwtToken = getJwtToken(request);

        if(jwtToken != null) {
            if (!jwtService.validateAccessToken(jwtToken))
                throw new SecurityException("Access token is not valid");

            String email = jwtService.getSubject(jwtToken);
            User user = userService.getUserByEmail(email);
            AppUserDetails appUserDetails = new AppUserDetails(user);

            var usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(appUserDetails, null, appUserDetails.getAuthorities());
            usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
        }

        filterChain.doFilter(request, response);
    }



    //private methods
    private String getJwtToken(HttpServletRequest request) {
        String authorizationHeader = request.getHeader("Authorization");

        if(authorizationHeader != null && authorizationHeader.startsWith("Bearer "))
            return authorizationHeader.substring(7);
        return null;
    }
}
