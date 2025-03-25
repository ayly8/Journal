package com.myjournal.repository;

import com.myjournal.model.JournalEntry;
import com.myjournal.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface UserRepo extends MongoRepository<User, String> {
   // find a user by their username
   Optional<User> findByUsername(String username);

   // find a user by their email
   Optional<User> findByEmail(String email);
}
