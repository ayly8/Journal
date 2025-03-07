package com.myjournal.controller;

import com.myjournal.model.JournalEntry;
import com.myjournal.service.JournalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@CrossOrigin(origins = "http://localhost:3000") // Allow React frontend
@RestController
@RequestMapping("/api/journal")
public class JournalController {

   private final JournalService journalService;

   @Autowired
   public JournalController(JournalService journalService) {
      this.journalService = journalService;
   }

   // find user by username
   @GetMapping("/username/{username}")
   public Optional<JournalEntry> getUserByUsername(@PathVariable String username) {
      return journalService.findByUsername(username);
   }

   // find user by email
   @GetMapping("/email/{email}")
   public Optional<JournalEntry> getUserByEmail(@PathVariable String email) {
      return journalService.findByEmail(email);
   }

   // save a journal entry
   @PostMapping("/save")
   public JournalEntry saveJournalEntry(@RequestBody JournalEntry journalEntry) {
      return journalService.saveJournalEntry(journalEntry);
   }
}
