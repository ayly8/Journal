package com.myjournal.backend.repository;

import com.myjournal.backend.model.JournalEntry;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;

public interface JournalEntryRepository extends MongoRepository<JournalEntry, String> {

   List<JournalEntry> findByUserId(String userId);

   List<JournalEntry> findByUserIdAndTitle(String userId, String title);
}
