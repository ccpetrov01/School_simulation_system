package ccpetrov01.studentApp.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class JWTUtils {
    @Value("${jwt.secret}")
    private String secretKey;
    @Value("${jwt.time}")
    private long jwtExpirationMs;

    public String generateToken(UserDetails userDetails){
        String roles = userDetails
                .getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        return Jwts
                .builder()
                .claim("roles", roles)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + jwtExpirationMs))
                .signWith(SignatureAlgorithm.HS256 , secretKey)
                .compact();
    }

    public String extractUsername(String token){
        return getClaims(token).getSubject();
    }

    public List<String> extractRoles(String token){
        String roles = getClaims(token).get("roles" , String.class);
        return Arrays.asList(roles.split(","));
    }

    private Claims getClaims(String token){
        return Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(token)
                .getBody();
    }



}
