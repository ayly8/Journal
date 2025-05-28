// UserRepository.java
package com.myjournal.backend.repository;

import com.myjournal.backend.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, String> {

   User findByUsername(String username);

   User findByEmail(String email);
}
