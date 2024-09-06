package kr.co.oasis.product.security.jwt;

import io.jsonwebtoken.*;
import kr.co.oasis.product.entity.domain.Member;
import kr.co.oasis.product.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import javax.crypto.spec.SecretKeySpec;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Optional;

@Service
public class TokenProvider {

    private final String secretKey;
    private final long accessExpirationTime;
    private final long refreshExpirationTime;
    private final String issuer;
    private static final String EMAIL_KEY = "email";
    private static final String AUTHORITIES_KEY = "role";

    private final MemberRepository memberRepository;

    public TokenProvider(
            @Value("${jwt.key}") String secretKey,
            @Value("${jwt.access-expire-time}") long accessExpirationTime,
            @Value("${jwt.refresh-expire-time}") long refreshExpirationTime,
            @Value("${jwt.issuer}") String issuer,
            MemberRepository memberRepository
    ) {
        this.secretKey = secretKey;
        this.accessExpirationTime = accessExpirationTime;
        this.refreshExpirationTime = refreshExpirationTime;
        this.issuer = issuer;
        this.memberRepository = memberRepository;
    }

    public TokenDto createToken(String email, String authorities) {
        Long now = System.currentTimeMillis();

        String accessToken = Jwts.builder()
                .setHeaderParam("typ", "JWT")
                .setHeaderParam("alg", "HS512")
                .setExpiration(Date.from(Instant.now().plus(accessExpirationTime, ChronoUnit.MINUTES)))
                .setSubject("access-token")
                .claim(EMAIL_KEY, email)
                .claim(AUTHORITIES_KEY, authorities)
                .signWith(new SecretKeySpec(secretKey.getBytes(), SignatureAlgorithm.HS512.getJcaName()))
                .compact();

        String refreshToken = Jwts.builder()
                .setHeaderParam("typ", "JWT")
                .setHeaderParam("alg", "HS512")
                .setExpiration(Date.from(Instant.now().plus(refreshExpirationTime, ChronoUnit.MINUTES)))
                .setSubject("refresh-token")
                .claim(EMAIL_KEY, email)
                .claim(AUTHORITIES_KEY, authorities)
                .signWith(new SecretKeySpec(secretKey.getBytes(), SignatureAlgorithm.HS512.getJcaName()))
                .compact();

        return new TokenDto(accessToken, refreshToken);
    }

    public Token tokenParser(String token) {
        Jws<Claims> claimsJws = Jwts.parserBuilder()
                .setSigningKey(new SecretKeySpec(secretKey.getBytes(), SignatureAlgorithm.HS512.getJcaName()))
                .build()
                .parseClaimsJws(token);

        String subject = claimsJws.getBody().getSubject();
        String email = claimsJws.getBody().get(EMAIL_KEY).toString();
        String role = claimsJws.getBody().get(AUTHORITIES_KEY).toString();
        Date expiration = claimsJws.getBody().getExpiration();

        return new Token(subject, email, role, expiration);
    }
}