package com.app.security.filters;

import com.app.security.token.JwtConfig;
import com.app.security.token.JwtTokenManager;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JwtAuthorizationFilter extends BasicAuthenticationFilter {

    private final JwtTokenManager jwtTokenManager;

    public JwtAuthorizationFilter(AuthenticationManager authenticationManager,
                                  JwtTokenManager jwtTokenManager) {
        super(authenticationManager);
        this.jwtTokenManager = jwtTokenManager;
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain chain)
            throws IOException, ServletException {

        String accessToken = request.getHeader(JwtConfig.ACCESS_TOKEN_HEADER_STRING);

        if (accessToken != null) {
            UsernamePasswordAuthenticationToken auth = jwtTokenManager.parseAccessToken(accessToken);
            SecurityContextHolder.getContext().setAuthentication(auth);
        }
        chain.doFilter(request, response);
    }
}
