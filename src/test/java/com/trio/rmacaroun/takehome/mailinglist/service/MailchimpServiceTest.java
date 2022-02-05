package com.trio.rmacaroun.takehome.mailinglist.service;

import com.trio.rmacaroun.takehome.mailinglist.client.MailchimpClient;
import com.trio.rmacaroun.takehome.mailinglist.dto.Contact;
import com.trio.rmacaroun.takehome.mailinglist.dto.Member;
import com.trio.rmacaroun.takehome.mailinglist.dto.MergeFields;
import com.trio.rmacaroun.takehome.mailinglist.dto.Status;
import com.trio.rmacaroun.takehome.mailinglist.mapper.ContactMemberMapper;
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
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.util.Assert.isTrue;

@SpringBootTest
public class MailchimpServiceTest {

    @MockBean
    private MailchimpClient mailchimpClient;

    @Autowired
    private ContactMemberMapper contactMemberMapper;

    @MockBean
    private ContactListService contactListService;

    @Autowired
    private MailchimpService mailchimpService;

    private final Date now = new Date();

    private Contact contact;

    private Member member;

    @BeforeEach
    public void setup() {
        this.contact = Contact.builder()
                .email("rmacaroun@hotmail.com")
                .firstName("Rafael")
                .lastName("Macaroun")
                .createdAt(now)
                .avatar("http://localhost:8080/myavatar.jpg")
                .id(100)
                .build();
        this.member = Member.builder()
                .emailAddress("rmacaroun@hotmail.com")
                .status(Status.SUBSCRIBED.getValue())
                .mergeFields(
                        MergeFields.builder()
                                .firstName("Rafael")
                                .lastName("Macaroun")
                                .createdAt(now)
                                .avatar("http://localhost:8080/myavatar.jpg")
                                .trioId(100)
                                .build())
                .build();
    }

    @Test
    public void testAddOrUpdateAudienceMembers() {
        when(this.contactListService.fetchAllContacts()).thenReturn(Arrays.asList(this.contact));
        when(this.mailchimpClient.updateAudienceMember(anyString(), anyString(), Mockito.any())).thenReturn(this.member);
        List<Contact> contacts = this.mailchimpService.addOrUpdateAudienceMembers();
        isTrue(!contacts.isEmpty(), "Contact List is empty");
        final Optional<Contact> first = contacts.stream().findFirst();
        isTrue(first.isPresent(), "Contact List first item is not present");
        Contact firstContact = first.get();
        isTrue(isNotBlank(firstContact.getEmail()), "Email is empty in the first contact");
        isTrue(isNotBlank(firstContact.getFirstName()), "First Name is empty in the first contact");
        isTrue(isNotBlank(firstContact.getLastName()), "Last Name is empty in the first contact");
    }
}
