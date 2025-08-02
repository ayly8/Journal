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

@RestController
@RequestMapping("/api/entries")
public class JournalEntryController {

   @Autowired
   private JournalEntryRepository journalEntryRepository;

   // Get all entries
   @GetMapping
   public List<JournalEntry> getEntriesForCurrentUser(@RequestParam String userId) {
      return journalEntryRepository.findByUserId(userId);
   }

   // Create new entry
   @PostMapping
   public ResponseEntity<?> createEntry(@RequestBody JournalEntryRequest request, HttpSession session) {
      System.out.println("Session ID: " + session.getId());
      System.out.println("User attribute: " + session.getAttribute("user"));

      String username = (String) session.getAttribute("user");
      if (username == null) {
         return ResponseEntity.status(403).body("Not authenticated");
      }

      JournalEntry entry = new JournalEntry();
      entry.setTitle(request.getTitle());
      entry.setEntry(request.getEntry());
      entry.setUserId(username); // use authenticated username, not what's in request
      entry.setDateSelected(request.getDateSelected());
      entry.setDateCreated(LocalDateTime.now());

      JournalEntry savedEntry = journalEntryRepository.save(entry);
      return ResponseEntity.ok(savedEntry);
   }

   // Get a single entry by ID
   @GetMapping("/{id}")
   public ResponseEntity<JournalEntry> getEntryById(@PathVariable String id) {
      Optional<JournalEntry> entry = journalEntryRepository.findById(id);
      return entry.map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
   }

   // Update an entry
   @PutMapping("/{id}")
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
   @DeleteMapping("/{id}")
   public ResponseEntity<Void> deleteEntry(@PathVariable String id) {
      journalEntryRepository.deleteById(id);
      return ResponseEntity.noContent().build();
   }
}
