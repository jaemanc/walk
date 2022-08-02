package com.org.walk;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class WalkApplication {

    public static void main(String[] args) {
        SpringApplication.run(WalkApplication.class, args);
    }

}
