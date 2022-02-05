package com.trio.rmacaroun.takehome.mailinglist.service;

import com.trio.rmacaroun.takehome.mailinglist.client.ContactListClient;
import com.trio.rmacaroun.takehome.mailinglist.dto.Contact;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.apache.commons.lang3.StringUtils.isNotBlank;
import static org.springframework.util.Assert.isTrue;

@SpringBootTest
public class ContactListServiceTest {

    @MockBean
    private ContactListClient contactListClient;

    @Autowired
    private ContactListService contactListService;

    private final Date now = new Date();

    private Contact contact;

    @BeforeEach
    private void setup() {
        this.contact = Contact.builder()
                .email("rmacaroun@hotmail.com")
                .firstName("Rafael")
                .lastName("Macaroun")
                .createdAt(now)
                .avatar("http://localhost:8080/myavatar.jpg")
                .id(100)
                .build();
    }

    @Test
    public void shouldListAllContacts() {
        Mockito.when(this.contactListService.listAllContacts()).thenReturn(Arrays.asList(this.contact));
        final List<Contact> contacts = this.contactListClient.listAllContacts();
        isTrue(!contacts.isEmpty(), "Contact List is empty");
        final Optional<Contact> first = contacts.stream().findFirst();
        isTrue(first.isPresent(), "Contact List first item is not present");
        Contact firstContact = first.get();
        isTrue(isNotBlank(firstContact.getEmail()), "Email is empty in the first contact");
        isTrue(isNotBlank(firstContact.getFirstName()), "First Name is empty in the first contact");
        isTrue(isNotBlank(firstContact.getLastName()), "Last Name is empty in the first contact");
        isTrue(firstContact.getId() != null, "Id is null");
        isTrue(firstContact.getCreatedAt() != null, "Date is null");
    }
}
