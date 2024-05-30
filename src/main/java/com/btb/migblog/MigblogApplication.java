package com.btb.migblog;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.retry.annotation.EnableRetry;

@SpringBootApplication
@EnableRetry
public class MigblogApplication {

    public static void main(String[] args) {
        SpringApplication.run(MigblogApplication.class, args);
    }

}
