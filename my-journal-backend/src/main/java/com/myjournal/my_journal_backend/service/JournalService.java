package com.myjournal.service;

import com.myjournal.model.JournalEntry;
import java.util.Optional;

/*
 * This file declares methods but does not implement it
 * Controller file will call this to process user requests
 */

public interface JournalService {
   // method to find a user by username
   Optional<JournalEntry> findByUsername(String username);

   // method to find a user by email
   Optional<JournalEntry> findByEmail(String email);

   // method to save journal entry
   JournalEntry saveJournalEntry(JournalEntry journalEntry);
}
