package com.myjournal.service;

import com.myjournal.model.JournalEntry;

import java.util.List;

public interface JournalService {
   // method to find journal entries by userId
   List<JournalEntry> findByUserId(String userId);

   // method to save journal entry
   JournalEntry saveJournalEntry(JournalEntry journalEntry);
}
