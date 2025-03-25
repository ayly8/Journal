package com.myjournal.service;

import com.myjournal.model.User;

import java.util.Optional;

public interface UserService {

   // Register a new user
   User registerUser(String username, String email, String password);

   // Authenticate the user and return JWT token
   String authenticate(String email, String password);

   // Retrieve user by email
   Optional<User> findByEmail(String email);
}
