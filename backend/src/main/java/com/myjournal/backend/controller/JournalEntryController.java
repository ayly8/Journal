package com.myjournal.backend.controller;

import com.myjournal.backend.model.JournalEntry;
import com.myjournal.backend.repository.JournalEntryRepository;

import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.myjournal.backend.dto.JournalEntryRequest;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

// defines API endpoints for journal entry-related operations
@RestController
@RequestMapping("/api/entries")
public class JournalEntryController {

   @Autowired
   private JournalEntryRepository journalEntryRepository;

   // Get all entries
   @GetMapping // GET /api/entries
   public ResponseEntity<?> getEntriesForCurrentUser(HttpSession session) {
      String username = (String) session.getAttribute("user"); // get current user
      // if user not logged in return 403 forbidden
      if (username == null) {
         return ResponseEntity.status(403).body("Not authenticated");
      }

      // find all entries related to the username and return those entries
      List<JournalEntry> entries = journalEntryRepository.findByUserId(username);
      return ResponseEntity.ok(entries);
   }

   // Create new entry
   @PostMapping // POST /api/entries
   public ResponseEntity<?> createEntry(@RequestBody JournalEntryRequest request, HttpSession session) {
      // verify if session id and user attribute exist
      System.out.println("Session ID: " + session.getId());
      System.out.println("User attribute: " + session.getAttribute("user"));

      // get current user
      String username = (String) session.getAttribute("user");
      // if no user return 403 server won't fufill request
      if (username == null) {
         return ResponseEntity.status(403).body("Not authenticated");
      }

      // create the new entry using the user responses
      JournalEntry entry = new JournalEntry();
      entry.setTitle(request.getTitle());
      entry.setEntry(request.getEntry());
      entry.setUserId(username); // use authenticated username, not what's in request
      entry.setDateSelected(request.getDateSelected());
      entry.setDateCreated(LocalDateTime.now());

      // save entry to database
      JournalEntry savedEntry = journalEntryRepository.save(entry);
      return ResponseEntity.ok(savedEntry);
   }

   // Get a single entry by ID
   @GetMapping("/{id}") // GET /api/entries/{id}
   public ResponseEntity<JournalEntry> getEntryById(@PathVariable String id) {
      Optional<JournalEntry> entry = journalEntryRepository.findById(id);
      return entry.map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
   }

   // Update an entry
   @PutMapping("/{id}") // PUT /api/entries/{id}
   public ResponseEntity<JournalEntry> updateEntry(@PathVariable String id,
         @RequestBody JournalEntryRequest updatedEntryRequest) {
      return journalEntryRepository.findById(id).map(entry -> {
         entry.setTitle(updatedEntryRequest.getTitle());
         entry.setEntry(updatedEntryRequest.getEntry());
         entry.setDateSelected(updatedEntryRequest.getDateSelected());
         return ResponseEntity.ok(journalEntryRepository.save(entry));
      }).orElse(ResponseEntity.notFound().build());
   }

   // Delete an entry
   @DeleteMapping("/{id}") // DELETE /api/entries/{id}
   public ResponseEntity<Void> deleteEntry(@PathVariable String id) {
      if (!journalEntryRepository.existsById(id)) {
         return ResponseEntity.notFound().build();
      }
      journalEntryRepository.deleteById(id);
      return ResponseEntity.noContent().build();
   }
}
