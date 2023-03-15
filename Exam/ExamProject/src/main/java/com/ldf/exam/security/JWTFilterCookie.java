package com.ldf.exam.security;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.ldf.exam.config.UserRepositoryUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;

@Component
public class JWTFilterCookie{

    @Autowired
    private UserRepositoryUserDetailsService userDetailsService;
    @Autowired
    private JWTUtil jwtUtil;
 //       @Autowired
//    private AuthenticationManager authManager;

    protected void doFilterInternal(HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain) throws ServletException, IOException {

        String jwt = jwtUtil.getJwtFromCookies(request);

        if (jwt == null || jwt.isEmpty()) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "User not logged in");
        } else {
            try {

                DecodedJWT decodedJwt = jwtUtil.validateTokenAndRetrieveDecodedJwt(jwt);
                String username = decodedJwt.getClaim("username").asString();
                Claim authClaim = decodedJwt.getClaim("authorities");
                List<SimpleGrantedAuthority> authorities;// = Arrays.asList(new SimpleGrantedAuthority("None"));
                UsernamePasswordAuthenticationToken authToken;

                if (authClaim != null && authClaim.asList(String.class) != null) {
                    System.out.println("From token");
                    authorities
                            = decodedJwt
                                    .getClaim("authorities")
                                    .asList(String.class)
                                    .stream()
                                    .map(SimpleGrantedAuthority::new)
                                    .collect(Collectors.toList());
                    authToken
                            = new UsernamePasswordAuthenticationToken(username, null, authorities);
                    //No autentica
                } else {//If no authorities defined in token retrieve it from db
                    System.out.println("From db");
                    UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                    authorities = new ArrayList(userDetails.getAuthorities());
                    authToken
                            = new UsernamePasswordAuthenticationToken(username, userDetails.getPassword(), authorities);
                 //       authManager.authenticate(authToken);
                    //No autentica, probar que autentique
                }

                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request)); //¿?¿?                                       
                if (SecurityContextHolder.getContext().getAuthentication() == null) {
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                    System.out.println("Auth?:" + SecurityContextHolder.getContext().getAuthentication().isAuthenticated());
                }
            } catch (JWTVerificationException exc) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                response.getWriter().println("Invalid JWT Token:" + exc.getMessage());
            }
        }      
        filterChain.doFilter (request, response);
    }
}

