package com.example.flowlet;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class FlowletApplication {

    static void main(String[] args) {
        SpringApplication.run(FlowletApplication.class, args);
    }

}
