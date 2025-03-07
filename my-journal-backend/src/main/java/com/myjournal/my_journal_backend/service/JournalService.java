package com.myjournal.service;

import com.myjournal.model.JournalEntry;
import java.util.Optional;

public interface JournalService {
   // find a user by username
   Optional<JournalEntry> findByUsername(String username);

   // find a user by email
   Optional<JournalEntry> findByEmail(String email);

   // additional business methods (e.g., saving, updating users)
   JournalEntry saveJournalEntry(JournalEntry journalEntry);
}
