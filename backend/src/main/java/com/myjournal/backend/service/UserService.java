package com.myjournal.backend.service;

import com.myjournal.backend.model.User;
import com.myjournal.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

// service layer that will handle business logic
@Service
public class UserService {

   @Autowired
   private UserRepository userRepo; // interact with User collection in MongoDB

   @Autowired
   private PasswordEncoder encoder; // hash and verify passwords

   // register new user
   public User register(String username, String email, String rawPassword) {
      // check if email is already taken
      if (userRepo.findByEmail(email) != null)
         throw new RuntimeException("Email already in use");
      // check if username is already taken
      if (userRepo.findByUsername(username) != null)
         throw new RuntimeException("Username already in use");

      // create new User
      User user = new User();
      user.setUsername(username);
      user.setEmail(email);
      user.setPassword(encoder.encode(rawPassword));
      user.setRoles(java.util.List.of("USER")); // "USER" is default role

      // save user to the database
      return userRepo.save(user);
   }

   // find user by email
   public Optional<User> findByEmail(String email) {
      return Optional.ofNullable(userRepo.findByEmail(email));
   }

   // validate login credentials
   public boolean validateUser(String username, String rawPassword) {
      // find user by username
      User user = userRepo.findByUsername(username);
      // if username doesn't exist return false
      if (user == null) {
         return false;
      }
      // compare raw password with the stored encrypted password
      return encoder.matches(rawPassword, user.getPassword());
   }
}
