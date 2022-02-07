package com.trio.rmacaroun.takehome.mailinglist.config;

import com.trio.rmacaroun.takehome.mailinglist.decoder.FeignCustomErrorDecoder;
import feign.codec.ErrorDecoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FeignClientConfig {

    @Bean
    public ErrorDecoder errorDecoder() {
        return new FeignCustomErrorDecoder();
    }
}
