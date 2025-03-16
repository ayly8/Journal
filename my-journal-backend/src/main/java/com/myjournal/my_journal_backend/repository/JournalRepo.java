package com.myjournal.repository;

import com.myjournal.model.JournalEntry;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

/*
 * This file is responsible for talking directly to MongoDB
 * It will help save, find, and retrieve data from the database
 */

@Repository
public interface JournalRepo extends MongoRepository<JournalEntry, String> {
   // manages JournalEntry objects and gives built-in methods

   // custom queries
   // find a user by their username
   Optional<JournalEntry> findByUsername(String username);

   // find a user by their email
   Optional<JournalEntry> findByEmail(String email);
}
