package com.book.admin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan({"com.book.common.entity"})
public class BookBackEndApplication {

    public static void main(String[] args) {
        SpringApplication.run(BookBackEndApplication.class, args);
    }

}

