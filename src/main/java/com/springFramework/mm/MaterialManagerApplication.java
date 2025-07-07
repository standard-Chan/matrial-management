package com.springFramework.mm;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MaterialManagerApplication {
    public static void main(String[] args) {
        SpringApplication.run(MaterialManagerApplication.class, args);
        System.out.println("Start Material Management Server");
    }
}