package com.animalog;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AnimaLogApplication {
    public static void main(String[] args) {
        SpringApplication.run(AnimaLogApplication.class, args);
        System.out.println("🐾 AnimaLog Pet Healthcare System Started Successfully...");
    }
}
