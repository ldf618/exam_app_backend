package com.ldf.exam.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import java.util.Arrays;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseCookie;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.web.util.WebUtils;

@Component
public class JWTUtil {

    @Value("${jwt_secret}")
    private String secret;
    @Value("${jwt_expiration_ms}")
    private int expirationMs;
    @Value("${jwt_cookie_name}")
    private String cookieName;    

    public String generateToken(String username) throws IllegalArgumentException, JWTCreationException {
        return JWT.create()
                .withSubject("User Details")
                .withClaim("username", username)
                .withIssuedAt(new Date())
                .withIssuer("Exam app")
                .withExpiresAt(new Date(new Date().getTime() + expirationMs)) //one day expiration
                .sign(Algorithm.HMAC256(secret));
    }
    
    public String generateToken(String username, List <GrantedAuthority> grantedAuthorities) throws IllegalArgumentException, JWTCreationException {
        return JWT.create()
                .withSubject("User Details")
                .withClaim("username", username)
                .withClaim("authorities",
                        grantedAuthorities.stream()
                                .map(GrantedAuthority::getAuthority)
				.collect(Collectors.toList()))
                .withIssuedAt(new Date())
                .withIssuer("Exam app")
                .withExpiresAt(new Date(new Date().getTime() + expirationMs)) //one day expiration
                .sign(Algorithm.HMAC256(secret));
    }

    public String validateTokenAndRetrieveSubject(String token)throws JWTVerificationException {
        JWTVerifier verifier = JWT.require(Algorithm.HMAC256(secret))
                .withSubject("User Details")
                .withIssuer("Exam app")
                .build();
        DecodedJWT jwt = verifier.verify(token);
        System.out.println(jwt.getClaim("authorities"));
        return jwt.getClaim("username").asString();
    }
    
    public DecodedJWT validateTokenAndRetrieveDecodedJwt(String token)throws JWTVerificationException {
        JWTVerifier verifier = JWT.require(Algorithm.HMAC256(secret))
                .withSubject("User Details")
                .withIssuer("Exam app")
                .build();
        DecodedJWT jwt = verifier.verify(token);
        return jwt;
    }
    
    public String getJwtFromCookies(HttpServletRequest request) {
        Cookie cookie = WebUtils.getCookie(request, cookieName);
        if (cookie != null) {
          return cookie.getValue();
        } else {
          return null;
        }
    }
    
    public ResponseCookie generateJwtCookie(String username, List <GrantedAuthority> grantedAuthorities) {
      String jwt = generateToken(username, grantedAuthorities);
      ResponseCookie cookie = 
              ResponseCookie
              .from(cookieName, jwt)
              .path("/api")
              .maxAge(24 * 60 * 60)
              .httpOnly(true)
              .secure(true)
              .sameSite("none")
              .build();
      return cookie;
    }
    
    public ResponseCookie getCleanJwtCookie() {
      ResponseCookie cookie = ResponseCookie.from(cookieName, null).path("/api").build();
      return cookie;
    }    
    

}