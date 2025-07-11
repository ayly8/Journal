package com.myjournal.backend.config;

import io.github.cdimascio.dotenv.Dotenv;

// loads the contents from the .env file
public class EnvLoader {
      // starts loading the MongoDB credentials from .env file
      public static void loadEnv() {
            Dotenv dotenv = Dotenv.configure()
                        .directory("./") // looks in backend/.env directory
                        .filename(".env") // finds and uses the .env file
                        .load(); // loads the environment variables

            // MongoDB connection using the loaded environment variables
            String uri = String.format(
                        "mongodb+srv://%s:%s@%s/%s?retryWrites=true&w=majority",
                        dotenv.get("MONGO_USERNAME"),
                        dotenv.get("MONGO_PASSWORD"),
                        dotenv.get("MONGO_CLUSTER"),
                        dotenv.get("MONGO_DB"));

            // set MongoDB URI as system property so Spring Boot can use it
            // URI - Uniform Resource Identifier - string that identifies a resource
            System.setProperty("spring.data.mongodb.uri", uri);

            // test printing
            System.out.println("MongoDB URI: " + uri);
      }
}
