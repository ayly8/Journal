package com.myjournal.service;

import com.myjournal.model.User;
import com.myjournal.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

   private final UserRepo userRepo; // ✅ Correct repository reference
   private final BCryptPasswordEncoder passwordEncoder;

   @Autowired
   public UserServiceImpl(UserRepo userRepo) {
      this.userRepo = userRepo;
      this.passwordEncoder = new BCryptPasswordEncoder();
   }

   @Override
   public User registerUser(String username, String email, String password) {
      User user = new User();
      user.setUsername(username);
      user.setEmail(email);
      user.setPassword(passwordEncoder.encode(password));
      return userRepo.save(user);
   }

   @Override
   public String authenticate(String email, String password) {
      Optional<User> userOptional = userRepo.findByEmail(email); // ✅ Correctly using UserRepo

      if (userOptional.isPresent()) {
         User user = userOptional.get();

         if (passwordEncoder.matches(password, user.getPassword())) {
            return "JWT_TOKEN"; // Return a placeholder token for now
         } else {
            throw new IllegalArgumentException("Invalid credentials");
         }
      } else {
         throw new IllegalArgumentException("User not found");
      }
   }

   @Override
   public Optional<User> findByEmail(String email) {
      return userRepo.findByEmail(email); // ✅ Correctly returning Optional<User>
   }
}
