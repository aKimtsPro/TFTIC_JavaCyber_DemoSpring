package be.tftic.spring.demo.utils.jwt;

import be.tftic.spring.demo.api.config.security.jwt.JwtProperties;
import be.tftic.spring.demo.domain.entity.User;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Date;

@Component
public class JwtProvider {

    private final JwtProperties jwtProps;

    public JwtProvider(JwtProperties jwtProps) {
        this.jwtProps = jwtProps;
    }

    public String generateToken(User user){
        JWTCreator.Builder builder = JWT.create()
                .withSubject( user.getUsername() )
                .withIssuedAt( Instant.now() )
                .withExpiresAt( Instant.now().plusMillis(jwtProps.getExpiresAt()) )
                .withClaim("id", user.getId())
                .withClaim("role", user.getAuthorities().stream()
                        .map(GrantedAuthority::getAuthority)
                        .toList()
                );
        if( user.getAdmin() != null)
            builder.withClaim("email", user.getAdmin().getEmail());

        return builder.sign( Algorithm.HMAC512(jwtProps.getSecret()) );
    }

    public String extractToken(HttpServletRequest req){
        String header = req.getHeader(jwtProps.getHeaderName());
        if( header == null || !header.startsWith("Bearer ") )
            return null;

        String token = header.replace("Bearer ", "").trim();
        if( token.isEmpty() )
            return null;

        return token;
    }

    public boolean validateToken( String token ){
        try {
            DecodedJWT jwt = JWT.require(Algorithm.HMAC512(jwtProps.getSecret()))
                    .acceptExpiresAt(jwtProps.getExpiresAt())
                    .build()
                    .verify(token);

            return jwt.getClaim("role") != null &&
                    jwt.getIssuedAt().before(new Date());
        }
        catch (JWTVerificationException ex) {
            return false;
        }
    }

    public String getUsername(String token){
        return JWT.decode(token)
                .getSubject();
    }
}
