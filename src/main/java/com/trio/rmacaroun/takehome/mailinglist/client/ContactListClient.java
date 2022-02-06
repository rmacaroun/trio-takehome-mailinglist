package com.trio.rmacaroun.takehome.mailinglist.client;

import com.trio.rmacaroun.takehome.mailinglist.dto.Contact;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@FeignClient(
        value = "contactlist-client",
        url = "${application.mockapi.contactList.baseurl}"
)
public interface ContactListClient {

    @GetMapping(path = "${application.mockapi.contactList.endpoint}")
    List<Contact> listAllContacts();
}
