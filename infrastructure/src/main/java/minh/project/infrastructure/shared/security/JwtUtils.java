package minh.project.infrastructure.shared.security;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.time.Duration;
import java.util.Date;
import java.util.function.Function;

@Component
@Slf4j
public class JwtUtils {

    @Value("${app.jwt.secret.access}")
    private String SECRET_ACCESS;

    @Value("${app.jwt.secret.refresh}")
    private String SECRET_REFRESH;

    @Value("${app.jwt.duration.access}")
    private Duration ACCESS_DURATION;

    @Value("${app.jwt.duration.refresh}")
    private Duration REFRESH_DURATION;

    public String generateAccessToken(String email) {
        return createToken(email, SECRET_ACCESS, ACCESS_DURATION);
    }

    public String generateRefreshToken(String email) {
        return createToken(email, SECRET_REFRESH, REFRESH_DURATION);
    }

    private String createToken(String email, String secret, Duration duration) {

        long durationMillis = duration.toMillis();

        return Jwts.builder()
                .setSubject(email)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + durationMillis))
                .signWith(getSignKey(secret), SignatureAlgorithm.HS512)
                .compact();
    }

    private Key getSignKey(String secret) {
        byte[] keyBytes = Decoders.BASE64.decode(secret);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String accessTokenExtractEmail(String token) {
        return extractClaim(token, Claims::getSubject, SECRET_ACCESS);
    }

    public Date accessTokenExtractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration, SECRET_ACCESS);
    }

    public String refreshTokenExtractEmail(String token) {
        return extractClaim(token, Claims::getSubject, SECRET_REFRESH);
    }

    public Date refreshTokenExtractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration, SECRET_REFRESH);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver, String secret) {
        final Claims claims = extractAllClaims(token, secret);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token, String secret) {
        return Jwts.parser().setSigningKey(getSignKey(secret)).build().parseClaimsJws(token).getBody();
    }

    private Boolean isAccessTokenExpired(String token) {
        return accessTokenExtractExpiration(token).before(new Date());
    }

    private Boolean isRefreshTokenExpired(String token) {
        return refreshTokenExtractExpiration(token).before(new Date());
    }

    public Boolean validateAccessToken(String token, UserDetails userDetails) {
        final String email = accessTokenExtractEmail(token);
        return (email.equals(userDetails.getUsername()) && !isAccessTokenExpired(token));
    }

    public Boolean validateRefreshToken(String token, UserDetails userDetails) {
        final String email = refreshTokenExtractEmail(token);
        return (email.equals(userDetails.getUsername()) && !isRefreshTokenExpired(token));
    }
}
