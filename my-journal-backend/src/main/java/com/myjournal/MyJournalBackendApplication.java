package com.myjournal;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class MyJournalBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(MyJournalBackendApplication.class, args);
	}

	// add BCryptPasswordEncoder bean
	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

}
