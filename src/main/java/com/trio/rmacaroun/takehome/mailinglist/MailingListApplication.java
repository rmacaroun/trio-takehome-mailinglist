package com.trio.rmacaroun.takehome.mailinglist;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class MailingListApplication {

    public static void main(String[] args) {
        SpringApplication.run(MailingListApplication.class, args);
    }
}
