package main.java.com.myjournal.backend.config;

import io.github.cdimascio.dotenv.Dotenv;

public class EnvLoader {
   public static void loadEnv() {
      Dotenv dotenv = Dotenv.configure()
            .directory("./") // Looks in backend/.env
            .filename(".env")
            .load();

      String uri = String.format(
            "mongodb+srv://%s:%s@%s/%s?retryWrites=true&w=majority",
            dotenv.get("MONGO_USERNAME"),
            dotenv.get("MONGO_PASSWORD"),
            dotenv.get("MONGO_CLUSTER"),
            dotenv.get("MONGO_DB"));

      System.setProperty("MONGO_URI", uri);

      // test printing
      System.out.println("MongoDB URI: " + uri);
   }
}
