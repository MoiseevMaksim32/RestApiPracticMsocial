package com.example.RestApiPracticMsocial.JWT;

import com.example.RestApiPracticMsocial.Model.Users;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@Component
@PropertySource("application.properties")
@Slf4j
public class JwtProvider {
    private final String jwtAccessKey;
    private final String jwtRefreshKey;

    public JwtProvider(@Value("${jwt.secret_access}") String AccessKey, @Value("${jwt.secret_refresh}") String RefreshKey) {
        this.jwtAccessKey = AccessKey;
        this.jwtRefreshKey = RefreshKey;
    }

    public String generateAccessToken(@NotNull Users users) {
        final LocalDateTime now = LocalDateTime.now();
        final Instant accessExpirationInstant = now.plusMinutes(60).atZone(ZoneId.systemDefault()).toInstant();
        final Date accessExpiration = Date.from(accessExpirationInstant);
        return Jwts.builder()
                .subject(users.getUsersName())
                .expiration(accessExpiration)
                .signWith(SignatureAlgorithm.HS256, jwtAccessKey)
                .compact();
    }

    public String generateRefreshToken(@NotNull Users users) {
        final LocalDateTime now = LocalDateTime.now();
        final Instant accessExpirationInstant = now.plusDays(30).atZone(ZoneId.systemDefault()).toInstant();
        final Date accessExpiration = Date.from(accessExpirationInstant);
        return Jwts.builder()
                .subject(users.getUsersName())
                .expiration(accessExpiration)
                .signWith(SignatureAlgorithm.HS256, jwtRefreshKey)
                .compact();
    }

    private boolean validateToken(@NotNull String token, @NotNull String secret) {
        try {
            Jwts.parser().setSigningKey(secret).build().parseClaimsJws(token);
            return true;
        } catch (ExpiredJwtException exc) {
            log.error(exc.getMessage());
            System.err.println(exc.getMessage());
        } catch (UnsupportedJwtException exc) {
            log.error(exc.getMessage());
            System.err.println(exc.getMessage());
        } catch (MalformedJwtException exc) {
            log.error(exc.getMessage());
            System.err.println(exc.getMessage());
        } catch (io.jsonwebtoken.security.SignatureException exc) {
            log.error(exc.getMessage());
            System.err.println(exc.getMessage());
        } catch (Exception exc) {
            log.error(exc.getMessage());
            System.err.println(exc.getMessage());
        }
        return false;
    }

    public boolean validateAccessToken(@NotNull String accessToken) {
        return validateToken(accessToken, jwtAccessKey);
    }

    public boolean validateRefreshToken(@NotNull String refreshToken) {
        return validateToken(refreshToken, jwtRefreshKey);
    }

    public Claims getAccessClaims(@NotNull String token) {
        return getClaims(token, jwtAccessKey);
    }

    public Claims getRefreshClaims(@NotNull String token) {
        return getClaims(token, jwtRefreshKey);
    }

    private Claims getClaims(@NotNull String token, @NotNull String secret) {
        return Jwts.parser()
                .setSigningKey(secret)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }


}
