package com.myjournal.backend.controller;

import com.myjournal.backend.model.JournalEntry;
import com.myjournal.backend.repository.JournalEntryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
   public List<JournalEntry> getAllEntries() {
      return journalEntryRepository.findAll();
   }

   // Create new entry
   @PostMapping
   public JournalEntry createEntry(@RequestBody JournalEntry entry) {
      entry.setDateCreated(LocalDateTime.now());
      return journalEntryRepository.save(entry);
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
   public ResponseEntity<JournalEntry> updateEntry(@PathVariable String id, @RequestBody JournalEntry updatedEntry) {
      return journalEntryRepository.findById(id).map(entry -> {
         entry.setTitle(updatedEntry.getTitle());
         entry.setEntry(updatedEntry.getEntry());
         return ResponseEntity.ok(journalEntryRepository.save(entry));
      }).orElse(ResponseEntity.notFound().build());
   }

   // Delete an entry
   @DeleteMapping("/{id}")
   public ResponseEntity<Void> deleteEntry(@PathVariable String id) {
      journalEntryRepository.deleteById(id);
      return ResponseEntity.noContent().build();
   }

   // Get all entries by userId
   @GetMapping("/user/{userId}")
   public List<JournalEntry> getEntriesByUserId(@PathVariable String userId) {
      return journalEntryRepository.findByUserId(userId);
   }
}
