package com.myjournal.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "Users") // maps this class to the "Users" collection in MongoDB
public class JournalEntry {

   @Id
   private String id; // MongoDB unique ObjectId (String)

   private String username;
   private String email;
   private String password;

}