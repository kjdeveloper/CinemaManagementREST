package com.app.security.token;

import com.app.dto.data.RefreshTokenDto;
import com.app.dto.data.Tokens;
import com.app.exception.AppException;
import com.app.model.User;
import com.app.repository.UserRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class JwtTokenManager {

    private final SecretKey secretKey;
    private final UserRepository userRepository;

    private final static String ACCESS_TOKEN_TIME_FOR_REFRESH_TOKEN = "accessTokenTime";

    //Generate tokens

    public Tokens generateTokens(Authentication authentication) {
        String username = authentication.getName();
        User userFromDb = userRepository
                .findByUsername(username)
                .orElseThrow(() -> new AppException("User with username " + username + " not found"));

        Date expirationAccessTokenTime = new Date(System.currentTimeMillis() + JwtConfig.ACCESS_TOKEN_EXPIRATION_TIME);
        Long expirationAccessTokenTimeInMillis = System.currentTimeMillis() + JwtConfig.ACCESS_TOKEN_EXPIRATION_TIME;
        Date expirationRefreshTokenTime = new Date(System.currentTimeMillis() + JwtConfig.REFRESH_TOKEN_EXPIRATION_TIME);
        Date issuedTime = new Date();

        String accessToken = Jwts
                .builder()
                .setSubject(userFromDb.getId().toString())
                .setIssuedAt(issuedTime)
                .setExpiration(expirationAccessTokenTime)
                .signWith(secretKey)
                .compact();

        String refreshToken = Jwts
                .builder()
                .setSubject(userFromDb.getId().toString())
                .setIssuedAt(issuedTime)
                .setExpiration(expirationRefreshTokenTime)
                .claim(ACCESS_TOKEN_TIME_FOR_REFRESH_TOKEN, expirationAccessTokenTimeInMillis)
                .signWith(secretKey)
                .compact();

        return Tokens
                .builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    public Tokens generateTokens(RefreshTokenDto refreshTokenDto) {

        if (refreshTokenDto == null) {
            throw new AppException("refresh token object is null");
        }

        String token = refreshTokenDto.getToken();
        String tokenToParse = JwtConfig.ACCESS_TOKEN_PREFIX + token;

        Long id = getId(tokenToParse);
        Long expirationAccessTokenTimeInMillis = Long.parseLong(getClaims(tokenToParse).get(ACCESS_TOKEN_TIME_FOR_REFRESH_TOKEN).toString());

        if (new Date(expirationAccessTokenTimeInMillis).before(new Date())) {
            throw new AppException("cannot refresh tokens");
        }

        Date issuedTime = new Date();
        Date expirationAccessTokenTime = new Date(System.currentTimeMillis() + JwtConfig.ACCESS_TOKEN_EXPIRATION_TIME);
        Date expirationRefreshTokenTime = getClaims(tokenToParse).getExpiration();

        String accessToken = Jwts
                .builder()
                .setSubject(id.toString())
                .setIssuedAt(issuedTime)
                .setExpiration(expirationAccessTokenTime)
                .signWith(secretKey)
                .compact();

        String refreshToken = Jwts
                .builder()
                .setSubject(id.toString())
                .setIssuedAt(issuedTime)
                .setExpiration(expirationRefreshTokenTime)
                .claim(ACCESS_TOKEN_TIME_FOR_REFRESH_TOKEN, expirationAccessTokenTimeInMillis)
                .signWith(secretKey)
                .compact();

        return Tokens.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }



    //Parsing tokens



    public UsernamePasswordAuthenticationToken parseAccessToken(String token) {
        if (!token.startsWith(JwtConfig.ACCESS_TOKEN_PREFIX)) {
            throw new AppException("get claims - token has incorrect format");
        }

        if (!isTokenValid(token)) {
            throw new AppException("get claims - token is not valid");
        }

        String username = getUsername(token);
        List<GrantedAuthority> authorities = getAuthorities(token);
        return new UsernamePasswordAuthenticationToken(username, null, authorities);
    }

    private List<GrantedAuthority> getAuthorities(String token) {
        Long id = getId(token);
        var user = userRepository
                .findById(id)
                .orElseThrow(() -> new AppException("no user with id " + id + "."));

        return user
                .getRoles()
                .stream()
                .map(role -> new SimpleGrantedAuthority(role.getName()))
                .collect(Collectors.toList());
    }

    private Long getId(String token) {
        return Long.parseLong(getClaims(token).getSubject());
    }

    private String getUsername(String token) {
        Long id = getId(token);
        return userRepository
                .findById(id)
                .orElseThrow(() -> new AppException("no user with id " + id + "."))
                .getUsername();
    }

    private Claims getClaims(String token) {
        return Jwts
                .parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(token.replace(JwtConfig.ACCESS_TOKEN_PREFIX, ""))
                .getBody();
    }

    private boolean isTokenValid(String token) {
        return getClaims(token).getExpiration().after(new Date());
    }
}
