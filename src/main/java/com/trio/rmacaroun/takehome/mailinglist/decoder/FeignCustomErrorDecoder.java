package com.trio.rmacaroun.takehome.mailinglist.decoder;

import feign.Response;
import feign.codec.ErrorDecoder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@Slf4j
public class FeignCustomErrorDecoder implements ErrorDecoder {

    @Override
    public Exception decode(String methodKey, Response response) {
        final HttpStatus httpStatus = HttpStatus.valueOf(response.status());
        switch (httpStatus) { // TODO use lambda switch
            case BAD_REQUEST:
            case NOT_FOUND:
                final String errorMessage = "Couldn't connect with external service.";
                log.error(errorMessage + " Status code {}, methodKey = {}", httpStatus, methodKey);
                return new ResponseStatusException(INTERNAL_SERVER_ERROR, errorMessage);
            default:
                final String genericErrorMessage = "Unknown error, contact support.";
                log.error(genericErrorMessage + " Status code {}, methodKey = {}", httpStatus, methodKey);
                return new ResponseStatusException(INTERNAL_SERVER_ERROR, response.reason());
        }
    }
}
