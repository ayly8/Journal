package com.myjournal.backend.repository;

import com.myjournal.backend.model.JournalEntry;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;

// provides methods to access and manage Journal Entry documents in "JournalEntries" collection
public interface JournalEntryRepository extends MongoRepository<JournalEntry, String> {
   // inherits CRUD operations like save(), findById(), deleteById(), etc.
   // Spring Data MongoDB will auto-generate the query based on the method name

   // custom method to find journal entries by userId
   List<JournalEntry> findByUserId(String userId);

   // custom method to find journal entries by userId and entry title
   List<JournalEntry> findByUserIdAndTitle(String userId, String title);
}
