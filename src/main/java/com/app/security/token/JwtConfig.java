package com.app.security.token;

public interface JwtConfig {

    long ACCESS_TOKEN_EXPIRATION_TIME = 300_000L;
    long REFRESH_TOKEN_EXPIRATION_TIME = 3000_000L;

    String ACCESS_TOKEN_HEADER_STRING = "Authorization";
    String ACCESS_TOKEN_PREFIX = "Bearer ";
}
