package org.example;

import java.util.Arrays;

import org.example.configuration.JpaConfig;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@EnableAutoConfiguration
@SpringBootApplication
@ComponentScan("org.example")
@Import(JpaConfig.class)
public class Main {

    // ... Bean definitions
    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }

   

}
