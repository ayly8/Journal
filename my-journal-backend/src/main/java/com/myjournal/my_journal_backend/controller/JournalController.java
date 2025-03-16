package com.myjournal.controller;

import com.myjournal.model.JournalEntry;
import com.myjournal.service.JournalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

/*
 * This file hadles API requests from React
 * It calls the service layer (JournalService.java) to process the data
 * Then returns the responses back to React
 */

@CrossOrigin(origins = "http://localhost:3000") // allow requests from React
@RestController // marks this class as a controller that handles HTTP requests
@RequestMapping("/api/journal") // endpoints in this class start with /api/journal
public class JournalController {

   // connect to service layer
   private final JournalService journalService;

   @Autowired
   public JournalController(JournalService journalService) {
      this.journalService = journalService;
   }

   /*
    * find user by username GET /api/journal/username/{username}
    * extracts username from URL and calls method in to search for user in database
    * returns user's journal entry if found
    */
   @GetMapping("/username/{username}")
   public Optional<JournalEntry> getUserByUsername(@PathVariable String username) {
      return journalService.findByUsername(username);
   }

   // find user by email by using same way as username
   @GetMapping("/email/{email}")
   public Optional<JournalEntry> getUserByEmail(@PathVariable String email) {
      return journalService.findByEmail(email);
   }

   /*
    * takes request body and calls service layer to save the journal entry to
    * MongoDB
    * then returns the saved journal entry
    */
   @PostMapping("/save")
   public JournalEntry saveJournalEntry(@RequestBody JournalEntry journalEntry) {
      return journalService.saveJournalEntry(journalEntry);
   }
}
