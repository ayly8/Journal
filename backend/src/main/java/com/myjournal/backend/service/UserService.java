package com.myjournal.backend.service;

import com.myjournal.backend.model.User;
import com.myjournal.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

   @Autowired
   private UserRepository userRepo;

   @Autowired
   private PasswordEncoder encoder;

   public User register(String username, String email, String rawPassword) {
      if (userRepo.findByEmail(email) != null)
         throw new RuntimeException("Email already in use");
      if (userRepo.findByUsername(username) != null)
         throw new RuntimeException("Username already in use");

      User user = new User();
      user.setUsername(username);
      user.setEmail(email);
      user.setPassword(encoder.encode(rawPassword));
      user.setRoles(java.util.List.of("USER"));

      return userRepo.save(user);
   }

   public Optional<User> findByEmail(String email) {
      return Optional.ofNullable(userRepo.findByEmail(email));
   }

   public boolean validateUser(String username, String rawPassword) {
      User user = userRepo.findByUsername(username);
      if (user == null) {
         return false;
      }
      return encoder.matches(rawPassword, user.getPassword());
   }
}
