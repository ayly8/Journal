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

// handles authenticated-related API endpoints for user registration/logout/login etc.
@RestController
@RequestMapping("/api/auth")
// allow frontend (React) to connect with backend
@CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true")
public class AuthController {

   // uses UserService to handle the logic
   @Autowired
   private UserService userService;

   // handles user registration
   @PostMapping("/register") // POST /api/auth/register
   public ResponseEntity<?> register(@RequestBody RegisterRequest body) {
      try {
         // calls service to create the user and save it to MongoDB
         User saved = userService.register(body.getUsername(),
               body.getEmail(),
               body.getPassword());
         // if no errors, return 200 OK with success message
         return ResponseEntity.ok("Registered user " + saved.getUsername());
      } catch (RuntimeException ex) {
         // if error, return 400 Bad Request
         return ResponseEntity.badRequest().body(ex.getMessage());
      }
   }

   // validates user credentials and creates a session if validated
   @PostMapping("/login") // POST /api/auth/login
   public ResponseEntity<?> login(@RequestBody LoginRequest body, HttpSession session) {
      boolean isValid = userService.validateUser(body.getUsername(), body.getPassword());
      if (isValid) {
         // store username in session to keep the user logged in
         session.setAttribute("user", body.getUsername());
         // if no errors, return success message
         return ResponseEntity.ok("Login successful");
      } else {
         // if errors (invalid credentials), return 401 Unauthorized
         return ResponseEntity.status(401).body("Invalid username or password");
      }
   }

   // returns username of currently logged in user
   @GetMapping("/me") // GET /api/auth/me
   public ResponseEntity<?> getCurrentUser(HttpSession session) {
      Object username = session.getAttribute("user");
      if (username != null) {
         // if user is logged in, return their username
         return ResponseEntity.ok(username);
      } else {
         // if user isn't logged in, return 401 Unauthorized
         return ResponseEntity.status(401).body("Not logged in");
      }
   }

   // logouts current user
   @PostMapping("/logout") // POST /api/auth/logout
   public ResponseEntity<?> logout(HttpSession session) {
      // invalidates session
      session.invalidate();
      return ResponseEntity.ok("Logged out");
   }

}
