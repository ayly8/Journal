package com.myjournal.controller;

import com.myjournal.model.User;
import com.myjournal.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:3000")
public class AuthController {

   private final UserService userService;

   @Autowired
   public AuthController(UserService userService) {
      this.userService = userService;
   }

   @PostMapping("/register")
   public User registerUser(@RequestBody User user) {
      return userService.registerUser(user.getUsername(), user.getEmail(), user.getPassword());
   }

   @PostMapping("/login")
   public String loginUser(@RequestBody User user) {
      return userService.authenticate(user.getEmail(), user.getPassword());
   }

   @GetMapping("/user/{email}")
   public Optional<User> getUserByEmail(@PathVariable String email) {
      return userService.findByEmail(email);
   }
}
