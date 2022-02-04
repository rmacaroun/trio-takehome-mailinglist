package com.trio.rmacaroun.takehome.mailinglist.config;

import feign.auth.BasicAuthRequestInterceptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MailchimpConfig {

    @Value("${application.mailchimp.api.username}")
    private String username;

    @Value("${application.mailchimp.api.key}")
    private String apiKey;

    @Bean
    public BasicAuthRequestInterceptor basicAuthRequestInterceptor() {
        return new BasicAuthRequestInterceptor(this.username, this.apiKey);
    }
}
