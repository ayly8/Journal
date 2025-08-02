// dto - data transfer object - carries data between layers of the application
package com.myjournal.backend.dto;

import java.time.LocalDateTime;

// dto that handles journal entry requests
public class JournalEntryRequest {
   private String title;
   private String entry;
   private String dateSelected;
   private String userId;
   private LocalDateTime dateCreated;

   // getters and setters
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

   public String getUserId() {
      return userId;
   }

   public void setUserId(String userId) {
      this.userId = userId;
   }

   public LocalDateTime getDateCreated() {
      return dateCreated;
   }

   public void setDateCreated(LocalDateTime dateCreated) {
      this.dateCreated = dateCreated;
   }
}
