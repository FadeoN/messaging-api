package com.messaging;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MessagingApiApplication {

    private static final Logger logger = LoggerFactory.getLogger(MessagingApiApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(MessagingApiApplication.class, args);
        logger.info("Simple log statement with inputs {}, {} and {}", 1,2,3);

    }
}
