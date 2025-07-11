// dto - data transfer object - carries data between layers of the application
package com.myjournal.backend.dto;

// dto that handles login requests
public class LoginRequest {
   // login form fields
   private String username;
   private String password;

   // constructors
   public LoginRequest() {
   }

   public LoginRequest(String username, String password) {
      this.username = username;
      this.password = password;
   }

   // getters and setters that are used by authcontroller/userservice files
   public String getUsername() {
      return username;
   }

   public void setUsername(String username) {
      this.username = username;
   }

   public String getPassword() {
      return password;
   }

   public void setPassword(String password) {
      this.password = password;
   }
}
