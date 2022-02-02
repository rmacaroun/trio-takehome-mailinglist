package com.trio.rmacaroun.takehome.mailinglist.controller;

import com.trio.rmacaroun.takehome.mailinglist.service.ContactListService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/contacts")
@RequiredArgsConstructor
public class ContactController {

    private final ContactListService contactListService;

    @GetMapping("/sync")
    public ResponseEntity syncContacts() {
        return ResponseEntity.ok(this.contactListService.fetchAllContacts());
    }
}
