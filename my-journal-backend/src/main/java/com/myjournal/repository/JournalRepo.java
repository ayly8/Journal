package com.myjournal.repository;

import com.myjournal.model.JournalEntry;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JournalRepo extends MongoRepository<JournalEntry, String> {
   // find all journal entries by userId (foreign key reference)
   List<JournalEntry> findByUserId(String userId);
}
