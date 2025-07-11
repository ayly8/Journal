package com.myjournal.backend.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

// connects to MongoDB collection called "Users"
@Document(collection = "Users")
public class User {

   // primary key (_id field in MongoDB)
   @Id
   private String id;

   // user fields stored in each document of the collection
   private String username;
   private String email;
   private String password;
   private List<String> roles;

   // constructors
   public User() {
   }

   public User(String username, String email, String password) {
      this.username = username;
      this.email = email;
      this.password = password;
   }

   // getters and setters for MongoDB document ID
   public String getId() {
      return id;
   }

   public void setId(String id) {
      this.id = id;
   }

   public String getUsername() {
      return username;
   }

   public void setUsername(String username) {
      this.username = username;
   }

   public String getEmail() {
      return email;
   }

   public void setEmail(String email) {
      this.email = email;
   }

   public String getPassword() {
      return password;
   }

   public void setPassword(String password) {
      this.password = password;
   }

   public List<String> getRoles() {
      return roles;
   }

   public void setRoles(List<String> roles) {
      this.roles = roles;
   }
}
