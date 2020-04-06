package com.app.security.filters;

import com.app.dto.data.AuthenticationDto;
import com.app.dto.data.Info;
import com.app.exception.AppException;
import com.app.repository.UserRepository;
import com.app.security.token.JwtTokenManager;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;

public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final JwtTokenManager jwtTokenManager;


    public JwtAuthenticationFilter(AuthenticationManager authenticationManager,
                                   UserRepository userRepository,
                                   JwtTokenManager jwtTokenManager) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.jwtTokenManager = jwtTokenManager;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request,
                                                HttpServletResponse response)
            throws AuthenticationException {
        try {
            AuthenticationDto authenticationDto = new ObjectMapper()
                    .readValue(request.getInputStream(), AuthenticationDto.class);

            return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    authenticationDto.getUsername(),
                    authenticationDto.getPassword(),
                    Collections.emptyList()
            ));
        } catch (Exception e) {
            e.printStackTrace();
            throw new AppException("attempt authentication: " + e.getMessage());
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request,
                                            HttpServletResponse response,
                                            FilterChain chain,
                                            Authentication authResult)
            throws IOException, ServletException {
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.getWriter().write(new ObjectMapper().writeValueAsString(jwtTokenManager.generateTokens(authResult)));
        response.getWriter().flush();
        response.getWriter().close();
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.getWriter().write(new ObjectMapper().writeValueAsString(Info.<String>builder()
                .error(failed.getMessage())
                .build()));
        response.getWriter().flush();
        response.getWriter().close();
    }
}
