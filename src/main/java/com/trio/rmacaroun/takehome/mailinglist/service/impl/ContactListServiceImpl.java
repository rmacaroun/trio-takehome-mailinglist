package com.trio.rmacaroun.takehome.mailinglist.service.impl;

import com.trio.rmacaroun.takehome.mailinglist.client.ContactListClient;
import com.trio.rmacaroun.takehome.mailinglist.dto.Contact;
import com.trio.rmacaroun.takehome.mailinglist.service.ContactListService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class ContactListServiceImpl implements ContactListService {

    private final ContactListClient contactListClient;

    @Override
    public List<Contact> fetchAllContacts() {
        final List<Contact> contacts = this.contactListClient.fetchAllContacts();
        log.info("Contacts were fetched successfully. Number of Available Contacts: {}", contacts.size());
        return contacts; // TODO manage exceptions
    }
}
