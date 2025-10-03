package com.myjournal.backend.repository;

import com.myjournal.backend.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

// provides methods to access and manage User documents in "User" collection
public interface UserRepository extends MongoRepository<User, String> {
   // inherits CRUD operations like save(), findById(), deleteById(), etc.
   // Spring Data MongoDB will auto-generate the query based on the method name

   // custom method to find users by their username
   User findByUsername(String username);

   // custom method to find users by their email
   User findByEmail(String email);
}
