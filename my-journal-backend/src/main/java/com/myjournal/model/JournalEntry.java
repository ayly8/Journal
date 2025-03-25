package com.myjournal.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Data
@Document(collection = "JournalEntries") // maps this class to the "JournalEntries" collection in MongoDB
public class JournalEntry {

   @Id
   private String id;

   private String userId;
   private String title;
   private String entry;
   private Date dateCreated = new Date();
}