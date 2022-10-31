package com.org.walk;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import javax.servlet.annotation.WebServlet;

@SpringBootApplication
@EnableJpaAuditing
@ServletComponentScan
@PropertySource("classpath:application.yml")
public class WalkApplication {

    public static void main(String[] args) {
        SpringApplication.run(WalkApplication.class, args);
    }

}
