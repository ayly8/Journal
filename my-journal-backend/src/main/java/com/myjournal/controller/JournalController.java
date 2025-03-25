package com.myjournal.controller;

import com.myjournal.model.JournalEntry;
import com.myjournal.service.JournalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/journal")
@CrossOrigin(origins = "http://localhost:3000")
public class JournalController {

   private final JournalService journalService;

   @Autowired
   public JournalController(JournalService journalService) {
      this.journalService = journalService;
   }

   @PostMapping("/save")
   public JournalEntry saveJournalEntry(@RequestBody JournalEntry journalEntry) {
      return journalService.saveJournalEntry(journalEntry);
   }
}
