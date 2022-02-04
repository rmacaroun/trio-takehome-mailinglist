package com.trio.rmacaroun.takehome.mailinglist.service.impl;

import com.trio.rmacaroun.takehome.mailinglist.dto.Contact;
import com.trio.rmacaroun.takehome.mailinglist.service.ContactListService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class ContactListServiceImpl implements ContactListService {

    @Value("${application.mockapi.contact.address}")
    private String mockApiContactAddress;

    private final RestTemplate restTemplate;

    @Override
    public List<Contact> fetchAllContacts() {
        final ResponseEntity<Contact[]> responseEntity = this.restTemplate.getForEntity(mockApiContactAddress, Contact[].class);
        final Contact[] contacts = responseEntity.getBody();
        log.info("Number of Available Contacts: {}", contacts.length);
        return Arrays.asList(contacts); // TODO manage null response
    }
}
