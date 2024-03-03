package kg.devcats.server.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import kg.devcats.server.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
@Slf4j
public class JwtService {

    @Value("${jwt.secret-key}")
    String jwtSecretKey;

    @Value("${jwt.access-expiry-in-minutes}")
    Integer minutes;

    @Value("${jwt.refresh-token-expiry-in-hours}")
    Integer refreshTokenExpiryInHours;


    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    public String generateToken(Map<String, Object> extraClaims, String email) {
        Instant issuedAt = Instant.now();
        return Jwts
                .builder()
                .setClaims(extraClaims)
                .setSubject(email)
                .setIssuedAt(Date.from(issuedAt))
                .setExpiration(Date.from(issuedAt.plus(minutes, ChronoUnit.MINUTES)))
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();

    }

    public String generateToken(User user) {
        Map<String, Object> extraClaims = new HashMap<>();
        extraClaims.put("role", user.getRole().getRussianRepresentation());
        extraClaims.put("profile_photo_url", user.getProfileImagePath());
        extraClaims.put("firstName", user.getFirstName());
        extraClaims.put("lastName", user.getLastName());
        return generateToken(extraClaims, user.getEmail());
    }

    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
    }

    public boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    private Claims extractAllClaims(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(jwtSecretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }


    public String generateRefreshToken(String email) {
        Instant issuedAt = Instant.now();
        return Jwts
                .builder()
                .setSubject(email)
                .setIssuedAt(Date.from(issuedAt))
                .setExpiration(Date.from(issuedAt.plus(refreshTokenExpiryInHours, ChronoUnit.HOURS)))
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }
}
