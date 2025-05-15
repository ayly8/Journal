package com.myjournal.backend;

import org.springframework.web.bind.annotation.*;

@RestController
public class HelloController {

   @GetMapping("/hello")
   public String sayHello() {
      return "Hello from journal backend!";
   }
}
