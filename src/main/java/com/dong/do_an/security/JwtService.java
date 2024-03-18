package com.dong.do_an.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService {

    private static final String SECRET_KEY = "+JPV2X2CWArTmXxa9EaqapN+fM4r/jj0xZsDFNfaJwb3C+gLZmPfRBJGSJW158JI8zmOmnmVsDiucqO+hZiAudSENnzEhhV8zEJ+GcEf1Fz9mPXtLE+V4hnFhLd+UzIKvh5pY2POqPmblfmQxVxywutMTW+v30az4TTOFjCWcQKevAPgSPsjiv5W6N8iYT6Aq7+c2X3T8UszxOOKZWSTqmiNTiPEB9UVkRvIrf149c/Puq03xfhEnV5klTsC8SOTIH9OVZJr1wbbrv4ieE66cI79uxnxmlkfi6I+RxpKsRKmdtX5fqU6hpJJw56TlKtY7yKrbECBmIXBJobhAQTrmlgH0FeYD6/4JDkwCQ2gsXo=";

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimResolver) {
        final Claims claims = extractAllClaims(token);
        return claimResolver.apply(claims);
    }

    public String generateToken(
            Map<String, Object> extraClaims,
            UserDetails userDetails
    ) {
        return Jwts
                .builder()
                .claims(extraClaims)
                .subject(userDetails.getUsername())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 24))
                .signWith(getSignInKey())
                .compact();
    }

    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    private Claims extractAllClaims(String token) {
        return Jwts
                .parser()
                .verifyWith(getSignInKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    private SecretKey getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
