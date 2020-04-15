package com.rined.smalltalk;

import io.sentry.Sentry;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SmallTalkApplication {
    public static void main(String[] args) {
        Sentry.capture("Application started");
        SpringApplication.run(SmallTalkApplication.class, args);
    }
}
