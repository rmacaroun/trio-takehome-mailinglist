package com.trio.rmacaroun.takehome.mailinglist.service;

import com.trio.rmacaroun.takehome.mailinglist.dto.Contact;

import java.util.List;

public interface ContactListService {

    List<Contact> fetchAllContacts();
}
