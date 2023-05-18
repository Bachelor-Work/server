package com.mosaics.mosaicsback.config;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import java.util.Base64;
import java.util.Collections;
import java.util.Date;

@Component
@RequiredArgsConstructor
public class JWTGenerator {

    private final UserDetailsService detailsService;

    public String generateToken(Authentication authentication) {
        String email = authentication.getName();
        Date currentDate = new Date();
        Date expireDate = new Date(currentDate.getTime() + SecurityConstants.JWT_EXPIRATION);

        return JWT.create()
                .withIssuer(email)
                .withIssuedAt(currentDate)
                .withExpiresAt(expireDate)
                .sign(Algorithm.HMAC256(Base64.getEncoder().encodeToString(SecurityConstants.JWT_SECRET.getBytes())));
    }

    public String getUsernameFromJWT(String token) {
        Algorithm algorithm = Algorithm.HMAC256(Base64.getEncoder().encodeToString(SecurityConstants.JWT_SECRET.getBytes()));
        JWTVerifier verifier = JWT.require(algorithm).build();
        DecodedJWT decodedJWT = verifier.verify(token);

        return detailsService.loadUserByUsername(decodedJWT.getIssuer()).getUsername();

    }

    public boolean validateToken(String token) {
        Algorithm algorithm = Algorithm.HMAC256(Base64.getEncoder().encodeToString(SecurityConstants.JWT_SECRET.getBytes()));
        JWTVerifier verifier = JWT.require(algorithm).build();
        DecodedJWT decodedJWT = verifier.verify(token);
        UserDetails user = detailsService.loadUserByUsername(decodedJWT.getIssuer());

        return new UsernamePasswordAuthenticationToken(user, null, Collections.emptyList()).isAuthenticated();
    }
}
