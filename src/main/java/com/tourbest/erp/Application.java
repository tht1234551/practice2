package com.tourbest.erp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@Configuration
@ComponentScan
@EnableAutoConfiguration
@EnableAspectJAutoProxy
public class Application {

    public static void main(String[] args) {
    	
        SpringApplication.run(Application.class, args);
    }
}