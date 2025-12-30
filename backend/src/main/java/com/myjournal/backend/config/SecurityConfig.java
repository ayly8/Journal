package com.myjournal.backend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
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

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

      @Autowired
      private CustomUserDetailsService customUserDetailsService;

      @Bean
      public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
            http
                        // for CORS to work with Spring Security
                        .cors(withDefaults())

                        // for session-based APIs
                        .csrf(csrf -> csrf
                                    .ignoringRequestMatchers("/api/**"))

                        .authorizeHttpRequests(auth -> auth
                                    // for browser preflight aka
                                    // HTTP request sent by browsers to a server to check for CORS permission
                                    .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()

                                    // for public endpoints
                                    .requestMatchers("/api/auth/**").permitAll()
                                    .requestMatchers("/api/entries/**").permitAll()

                                    // for everything else requires login
                                    .anyRequest().authenticated())

                        // disable the default login page
                        .formLogin(form -> form.disable())

                        // for custom user lookup
                        .userDetailsService(customUserDetailsService)

                        .sessionManagement(session -> session
                                    .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED));
            return http.build();
      }

      @Bean
      public CorsConfigurationSource corsConfigurationSource() {
            CorsConfiguration config = new CorsConfiguration();
            config.setAllowCredentials(true); // allow cookies/sessions
            // allowed origins
            config.setAllowedOrigins(Arrays.asList(
                        "http://localhost:5173", // developer link
                        "https://myjournalapp-prod.azurewebsites.net" // production link
            ));
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
