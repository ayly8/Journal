package com.myjournal.backend.service;

import com.myjournal.backend.model.User;
import com.myjournal.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

// tells Spring Security how to fetch a user from MongoDB during authentication
@Service
public class CustomUserDetailsService implements UserDetailsService {

   @Autowired
   private UserRepository userRepository; // fetch user data from the database

   @Override
   public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
      // find user by username
      User user = userRepository.findByUsername(username);
      // if user doesn't exist throw an exception
      if (user == null) {
         throw new UsernameNotFoundException("User not found with username: " + username);
      }

      // convert custom User object into a Spring Security UserDetails object
      return org.springframework.security.core.userdetails.User
            .withUsername(user.getUsername())
            .password(user.getPassword())
            .roles(user.getRoles().toArray(new String[0])) // convert roles (List<String>) to String[]
            .build();
   }
}
