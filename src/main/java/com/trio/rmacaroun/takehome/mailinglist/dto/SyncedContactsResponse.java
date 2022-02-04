package com.trio.rmacaroun.takehome.mailinglist.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SyncedContactsResponse implements Serializable {

    private static final long serialVersionUID = -5752131423158766916L;

    private Integer syncedContacts;

    private List<Contact> contacts;
}
