package com.myjournal.service;

import com.myjournal.model.JournalEntry;
import com.myjournal.repository.JournalRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

/*
 * This file implements the JournalService interface
 */

@Service // connects controllers to repositories
public class JournalServiceImpl implements JournalService {

   private final JournalRepo journalRepo;

   // injects the repository into the service
   // now this class can access database through JournalRepo
   @Autowired
   public JournalServiceImpl(JournalRepo journalRepo) {
      this.journalRepo = journalRepo;
   }

   // calls the repository method to fetch user from MongoDB by username
   @Override
   public Optional<JournalEntry> findByUsername(String username) {
      return journalRepo.findByUsername(username);
   }

   // calls the repository method to fetch user from MongoDB by email
   @Override
   public Optional<JournalEntry> findByEmail(String email) {
      return journalRepo.findByEmail(email);
   }

   // saves the journal entry using the repository
   @Override
   public JournalEntry saveJournalEntry(JournalEntry journalEntry) {
      return journalRepo.save(journalEntry);
   }
}
