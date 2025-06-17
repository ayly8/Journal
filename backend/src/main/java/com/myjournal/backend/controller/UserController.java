package com.myjournal.backend.controller;

import com.myjournal.backend.model.User;
import com.myjournal.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true")
public class UserController {

   @Autowired
   private UserRepository userRepository;

   // Get all users
   @GetMapping
   public List<User> getAllUsers() {
      return userRepository.findAll();
   }

   // Create new user
   @PostMapping
   public User createUser(@RequestBody User user) {
      return userRepository.save(user);
   }

   // Get a user by ID
   @GetMapping("/{id}")
   public ResponseEntity<User> getUserById(@PathVariable String id) {
      Optional<User> user = userRepository.findById(id);
      return user.map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
   }

   // Update a user
   @PutMapping("/{id}")
   public ResponseEntity<User> updateUser(@PathVariable String id, @RequestBody User updatedUser) {
      return userRepository.findById(id).map(user -> {
         user.setUsername(updatedUser.getUsername());
         user.setEmail(updatedUser.getEmail());
         return ResponseEntity.ok(userRepository.save(user));
      }).orElse(ResponseEntity.notFound().build());
   }

   // Delete a user
   @DeleteMapping("/{id}")
   public ResponseEntity<Void> deleteUser(@PathVariable String id) {
      userRepository.deleteById(id);
      return ResponseEntity.noContent().build();
   }

   // Return current user
   @GetMapping("/me")
   public ResponseEntity<?> getCurrentUser(Principal principal) {
      if (principal == null) {
         return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Not logged in");
      }

      return ResponseEntity.ok(principal.getName()); // or return user details
   }
}
