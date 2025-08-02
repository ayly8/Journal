package com.myjournal.backend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.myjournal.backend.service.CustomUserDetailsService;

import static org.springframework.security.config.Customizer.withDefaults;

import org.springframework.beans.factory.annotation.Autowired;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

   @Autowired
   private CustomUserDetailsService customUserDetailsService;

   @Bean
   public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
      http
            .cors(withDefaults()) // enable CORS - cross origin resource sharing
            .csrf(csrf -> csrf.disable()) // disable CSRF - cross site request forgery (ok for APIs using sessions)
            .authorizeHttpRequests(auth -> auth
                  .requestMatchers("/api/auth/**").permitAll() // allow anyone to access register/login endpoints
                  .requestMatchers("/api/entries/**").permitAll()
                  .anyRequest().authenticated()) // but everything else needs authentication
            .formLogin(form -> form.disable()) // disable default Spring Boot login form
            .userDetailsService(customUserDetailsService)
            .sessionManagement(session -> session
                  .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)); // create sessions only if needed
      return http.build();
   }

   @Bean
   public CorsConfigurationSource corsConfigurationSource() {
      CorsConfiguration config = new CorsConfiguration();
      config.setAllowCredentials(true); // allow cookies/sessions
      config.addAllowedOrigin("http://localhost:5173"); // allow frontend to access backend
      config.addAllowedHeader("*"); // allow all headers (Authorization, etc.)
      config.addAllowedMethod("*"); // allow all methods (GET, POST, etc.)

      UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
      source.registerCorsConfiguration("/**", config); // apply CORS config to all routes
      return source;
   }

   @Bean
   public PasswordEncoder passwordEncoder() {
      // hashes passwords before storing them
      return new BCryptPasswordEncoder();
   }
}
