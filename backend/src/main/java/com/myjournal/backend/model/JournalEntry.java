package com.myjournal.backend.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

// connects to MongoDB collection called "JournalEntries"
@Document(collection = "JournalEntries")
public class JournalEntry {

   // primary key (_id field in MongoDB)
   @Id
   private String id;

   // journal entry fields stored in each document of the collection
   private String userId;
   private String title;
   private String entry;
   private String dateSelected;
   private LocalDateTime dateCreated;

   // constructors
   public JournalEntry() {
   }

   public JournalEntry(String userId, String title, String entry, String dateSelected, LocalDateTime dateCreated) {
      this.userId = userId;
      this.title = title;
      this.entry = entry;
      this.dateSelected = dateSelected;
      this.dateCreated = dateCreated;
   }

   // getters and setters for Mongodb document ID
   public String getId() {
      return id;
   }

   public void setId(String id) {
      this.id = id;
   }

   public String getUserId() {
      return userId;
   }

   public void setUserId(String userId) {
      this.userId = userId;
   }

   public String getTitle() {
      return title;
   }

   public void setTitle(String title) {
      this.title = title;
   }

   public String getEntry() {
      return entry;
   }

   public void setEntry(String entry) {
      this.entry = entry;
   }

   public String getDateSelected() {
      return dateSelected;
   }

   public void setDateSelected(String dateSelected) {
      this.dateSelected = dateSelected;
   }

   public LocalDateTime getDateCreated() {
      return dateCreated;
   }

   public void setDateCreated(LocalDateTime dateCreated) {
      this.dateCreated = dateCreated;
   }
}
