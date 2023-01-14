package com.ldf.exam.config;

import com.ldf.exam.security.JWTFilter;
import java.util.Arrays;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
//import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

/**
 *
 * @author ldiez
 */
@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
//@EnableWebSecurity(debug = true)
public class SecurityConfig {

    @Autowired
    private JWTFilter filter;
    @Autowired
    private UserRepositoryUserDetailsService uds;
    @Value("${frontend_host}")
    private String host;

    /*
    @Bean
    public InMemoryUserDetailsManager userDetailsService() {

      // PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
      // System.out.println(encoder.encode("??????"));        

        UserDetails user = User.withUsername("user")
                .password("$2a$10$fg5w0YM3fIGfX25SdkLsTOiO0N1/mIE6E0a9T49lgeyrC2qb/9j2K")
                .roles("Student", "Consultant")
                .build();

        return new InMemoryUserDetailsManager(user);
    }
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
            throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    /*
    //hashed stored passwords must be prefixed with their known encoder identifier: {bcrypt} {sha256}
    @Bean
    public PasswordEncoder passwordEncoder() {
              PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
                System.out.println(encoder.encode("1234"));  
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        //PasswordEncoder encoder = new BCryptPasswordEncoder();
        //System.out.println(encoder.encode("????"));
        return new BCryptPasswordEncoder();
    }
    
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList(host));
        configuration.setAllowCredentials(Boolean.TRUE);
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));
        //configuration.setAllowedHeaders(Arrays.asList("authorization", "content-type", "x-auth-token"));
        configuration.setAllowedHeaders(Arrays.asList("*"));
//        configuration.setExposedHeaders(Arrays.asList("x-auth-token"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        /*
        http //HTTP Basic authentication
                .httpBasic()
                .and()
                .authorizeRequests()
                .antMatchers("/api/**").hasAnyRole("Student","Consultant")
                //.antMatchers(HttpMethod.DELETE, "/api/**").hasRole("ADMIN")
                .and()
                .csrf().disable()
                .formLogin().disable();

        return http.build();
         */

        http.csrf().disable()
            .httpBasic().disable()
            .cors()           
            .and()                
            .authorizeHttpRequests()
//            .antMatchers(HttpMethod.OPTIONS,"/api/**").permitAll()                
            .antMatchers("/api/auth/**").permitAll()
            .antMatchers("/api/**").hasAnyRole("Student", "Consultant")
            .and()
            .userDetailsService(uds)                
            .exceptionHandling()
            .authenticationEntryPoint(
                    (request, response, authException)
                    //-> response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized")
                    -> response.setStatus(HttpServletResponse.SC_UNAUTHORIZED)
            )

            .and()
            .sessionManagement()
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            //.and().headers().contentSecurityPolicy("frame-ancestors 'self' "+host)
                //.and().contentSecurityPolicy("frame-src 'self' http://localhost:8080/ ")
                           // .and().frameOptions().disable()
                ;

        http.addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
}
