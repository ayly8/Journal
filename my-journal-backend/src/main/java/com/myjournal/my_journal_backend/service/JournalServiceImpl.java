package com.myjournal.service;

import com.myjournal.model.JournalEntry;
import com.myjournal.repository.JournalRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class JournalServiceImpl implements JournalService {

   private final JournalRepo journalRepo;

   @Autowired // injects the repository into the service
   public JournalServiceImpl(JournalRepo journalRepo) {
      this.journalRepo = journalRepo;
   }

   @Override
   public Optional<JournalEntry> findByUsername(String username) {
      return journalRepo.findByUsername(username); // calls the repository method to find by username
   }

   @Override
   public Optional<JournalEntry> findByEmail(String email) {
      return journalRepo.findByEmail(email); // calls the repository method to find by email
   }

   @Override
   public JournalEntry saveJournalEntry(JournalEntry journalEntry) {
      return journalRepo.save(journalEntry); // saves the journal entry using the repository
   }
}
