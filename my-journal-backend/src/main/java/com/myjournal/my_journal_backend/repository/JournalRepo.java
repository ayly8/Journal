package com.myjournal.repository;

import com.myjournal.model.JournalEntry;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface JournalRepo extends MongoRepository<JournalEntry, String> {
   // add queries here if needed

   // find a user by their username
   Optional<JournalEntry> findByUsername(String username);

   // find a user by their email
   Optional<JournalEntry> findByEmail(String email);
}
