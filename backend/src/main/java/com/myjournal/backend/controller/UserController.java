package com.myjournal.backend.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import com.myjournal.backend.model.User;
import com.myjournal.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

// defines API endpoints for user-related operations
@RestController
@RequestMapping("/api/users")
// allow frontend (React) to connect with backend
@CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true")
public class UserController {

   // connects HTTP requests (get, create, etc.) to database actions using UserRepo
   @Autowired
   private UserRepository userRepository;

   // get all users in the database
   @GetMapping // GET /api/users
   public List<User> getAllUsers() {
      return userRepository.findAll();
   }

   // create and save new user in the database
   @PostMapping // POST /api/users
   public User createUser(@RequestBody User user) {
      return userRepository.save(user);
   }

   // returns a specific user by their MongoDB document ID
   @GetMapping("/{id}") // GET /api/users/{id}
   public ResponseEntity<User> getUserById(@PathVariable String id) {
      Optional<User> user = userRepository.findById(id);
      return user.map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build()); // returns 404 if not found
   }

   // update user's username and email
   @PutMapping("/{id}") // PUT /api/users/{id}
   public ResponseEntity<User> updateUser(@PathVariable String id, @RequestBody User updatedUser) {
      return userRepository.findById(id).map(user -> {
         user.setUsername(updatedUser.getUsername());
         user.setEmail(updatedUser.getEmail());
         return ResponseEntity.ok(userRepository.save(user));
      }).orElse(ResponseEntity.notFound().build());
   }

   // delete a user by their MongoDB document ID
   @DeleteMapping("/{id}") // DELETE /api/users/{id}
   public ResponseEntity<Void> deleteUser(@PathVariable String id) {
      userRepository.deleteById(id);
      return ResponseEntity.noContent().build(); // returns 204 no content
   }

   // return username of the current user
   @GetMapping("/me") // GET /api/users/me
   public ResponseEntity<?> getCurrentUser(Principal principal) {
      if (principal == null) {
         return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Not logged in");
      }

      return ResponseEntity.ok(principal.getName()); // return logged-in username
   }
}
