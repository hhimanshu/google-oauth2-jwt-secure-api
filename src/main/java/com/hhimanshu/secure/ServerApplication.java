package com.hhimanshu.secure;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ServerApplication {

  public static void main(String[] args) {
    System.out.println("Hello World");
    SpringApplication.run(ServerApplication.class, args);
  }
}
