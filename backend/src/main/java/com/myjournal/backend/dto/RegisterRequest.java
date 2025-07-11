// dto - data transfer object - carries data between layers of the application
package com.myjournal.backend.dto;

// dto that handles login requests
public class RegisterRequest {
   // register form fields
   private String username;
   private String email;
   private String password;

   // constructors
   public RegisterRequest() {
   }

   public RegisterRequest(String username, String email, String password) {
      this.username = username;
      this.email = email;
      this.password = password;
   }

   // getters and setters that are used by authcontroller/userservice files
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
}