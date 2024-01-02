package send.toyou.authservice.application.services.impl;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import send.toyou.authservice.application.services.JwtService;

import java.util.Date;

@Service
public class JwtServiceImpl implements JwtService {
    private final String secretToken;

    public JwtServiceImpl(@Value("${jwt.token}") String secretToken) {
        this.secretToken = secretToken;
    }

    @Override
    public String generateToken(String userId) {
        Date expirationDate = new Date(Long.MAX_VALUE);

        String token = Jwts.builder()
                .setSubject(userId)
                .setExpiration(expirationDate)
                .signWith(SignatureAlgorithm.HS256, this.secretToken)
                .compact();

        return token;
    }
}
