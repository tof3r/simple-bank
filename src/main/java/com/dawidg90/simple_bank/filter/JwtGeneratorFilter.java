package com.dawidg90.simple_bank.filter;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.core.env.Environment;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.crypto.SecretKey;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.stream.Collectors;

import static com.dawidg90.simple_bank.constants.ApplicationConstants.JWT_HEADER;
import static com.dawidg90.simple_bank.constants.ApplicationConstants.JWT_SECRET_DEFAULT;
import static com.dawidg90.simple_bank.constants.ApplicationConstants.JWT_SECRET_KEY;

public class JwtGeneratorFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (null != authentication) {
            Environment environment = getEnvironment();
            if (null != environment) {
                String secret = environment.getProperty(JWT_SECRET_KEY, JWT_SECRET_DEFAULT);
                SecretKey secretKey = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
                String jwt = Jwts.builder()
                        .issuer("SimpleBank")
                        .subject("JWT Token")
                        .claim("username", authentication.getName())
                        .claim("authorities", authentication.getAuthorities()
                                .stream()
                                .map(GrantedAuthority::getAuthority)
                                .collect(Collectors.joining(",")))
                        .issuedAt(Date.from(LocalDateTime.now().toInstant(ZoneOffset.UTC)))
                        .expiration(Date.from(LocalDateTime.now().plusHours(1).toInstant(ZoneOffset.UTC)))
                        .signWith(secretKey)
                        .compact();
                response.setHeader(JWT_HEADER, jwt);
            }
        }
        filterChain.doFilter(request, response);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        return !request.getServletPath().equals("/user"); //execute only when given path matches to login API path
    }
}
