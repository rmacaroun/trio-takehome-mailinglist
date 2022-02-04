package com.trio.rmacaroun.takehome.mailinglist.controller;

import com.trio.rmacaroun.takehome.mailinglist.dto.Contact;
import com.trio.rmacaroun.takehome.mailinglist.dto.SyncedContactsResponse;
import com.trio.rmacaroun.takehome.mailinglist.service.MailchimpService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/contacts")
@RequiredArgsConstructor
@Slf4j
public class ContactController {

    private final MailchimpService mailchimpService;

    @GetMapping("/sync")
    public ResponseEntity syncContacts() {
        final List<Contact> syncedContacts = this.mailchimpService.addOrUpdateAudienceMembers();
        log.info("Number of synced Contacts: ", syncedContacts.size());
        SyncedContactsResponse syncedContactsResponse = SyncedContactsResponse.builder()
                .syncedContacts(syncedContacts.size())
                .contacts(syncedContacts)
                .build();
        return ResponseEntity.ok(syncedContactsResponse);
    }
}
