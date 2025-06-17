package com.myjournal.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.myjournal.backend.dto.RegisterRequest;
import com.myjournal.backend.dto.LoginRequest;
import com.myjournal.backend.model.User;
import com.myjournal.backend.service.UserService;

import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true")
public class AuthController {

   @Autowired
   private UserService userService;

   @PostMapping("/register")
   public ResponseEntity<?> register(@RequestBody RegisterRequest body) {
      try {
         User saved = userService.register(body.getUsername(),
               body.getEmail(),
               body.getPassword());
         return ResponseEntity.ok("Registered user " + saved.getUsername());
      } catch (RuntimeException ex) {
         return ResponseEntity.badRequest().body(ex.getMessage());
      }
   }

   @PostMapping("/login")
   public ResponseEntity<?> login(@RequestBody LoginRequest body, HttpSession session) {
      boolean isValid = userService.validateUser(body.getUsername(), body.getPassword());
      if (isValid) {
         session.setAttribute("user", body.getUsername()); // store in session
         return ResponseEntity.ok("Login successful");
      } else {
         return ResponseEntity.status(401).body("Invalid username or password");
      }
   }

   @GetMapping("/me")
   public ResponseEntity<?> getCurrentUser(HttpSession session) {
      Object username = session.getAttribute("user");
      if (username != null) {
         return ResponseEntity.ok(username);
      } else {
         return ResponseEntity.status(401).body("Not logged in");
      }
   }

   @PostMapping("/logout")
   public ResponseEntity<?> logout(HttpSession session) {
      session.invalidate();
      return ResponseEntity.ok("Logged out");
   }

}
