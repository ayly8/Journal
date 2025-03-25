package com.myjournal.service;

import com.myjournal.model.JournalEntry;
import com.myjournal.repository.JournalRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JournalServiceImpl implements JournalService {

   private final JournalRepo journalRepo;

   // injects the repository into the service
   // now this class can access database through JournalRepo
   @Autowired
   public JournalServiceImpl(JournalRepo journalRepo) {
      this.journalRepo = journalRepo;
   }

   // retrieve journal entries by userId
   @Override
   public List<JournalEntry> findByUserId(String userId) {
      return journalRepo.findByUserId(userId);
   }

   // saves the journal entry using the repository
   @Override
   public JournalEntry saveJournalEntry(JournalEntry journalEntry) {
      return journalRepo.save(journalEntry);
   }
}
