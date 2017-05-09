package com.hhimanshu.secure.auth;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import java.time.Instant;
import java.util.Date;
import java.util.Optional;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;

@Component
public class AppTokenProvider {

  private static final long EXPIRATION_TIME_SECONDS = 864_000_000; // 10 days
  private static final String SECRET = "ThisIsASecret";
  private static final String TOKEN_PREFIX = "Bearer";
  private static final String HEADER_STRING = "Authorization";

  public static void addAuthentication(HttpServletResponse res, String username) {
    String JWT = Jwts.builder()
        .setSubject(username)
        .setExpiration(new Date(System.nanoTime() + EXPIRATION_TIME_SECONDS))
        .signWith(SignatureAlgorithm.HS512, SECRET)
        .compact();
    res.addHeader(HEADER_STRING, TOKEN_PREFIX + " " + JWT);
  }

  public static Optional<String> getUserFromToken(HttpServletRequest request) {
    final String token = request.getHeader(HEADER_STRING);

    if (token != null) {
      try {
        Claims body = Jwts.parser()
            .setSigningKey(SECRET)
            .parseClaimsJws(token.replace(TOKEN_PREFIX, ""))
            .getBody();
        final Instant expiration = body.getExpiration().toInstant();

        if (expiration.isBefore(Instant.now())) {
          return Optional.empty();
        }
        return Optional.of(body.getSubject());

      } catch (SignatureException e) {
        // invalid signature
      }
    }
    return Optional.empty();
  }
}
