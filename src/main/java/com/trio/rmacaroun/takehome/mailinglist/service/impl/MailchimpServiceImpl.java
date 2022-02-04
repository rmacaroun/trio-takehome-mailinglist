package com.trio.rmacaroun.takehome.mailinglist.service.impl;

import com.trio.rmacaroun.takehome.mailinglist.client.MailchimpClient;
import com.trio.rmacaroun.takehome.mailinglist.dto.Contact;
import com.trio.rmacaroun.takehome.mailinglist.dto.Member;
import com.trio.rmacaroun.takehome.mailinglist.mapper.ContactMemberMapper;
import com.trio.rmacaroun.takehome.mailinglist.service.ContactListService;
import com.trio.rmacaroun.takehome.mailinglist.service.MailchimpService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class MailchimpServiceImpl implements MailchimpService {

    @Value("${application.mailchimp.api.audience-id}")
    private String audienceId;

    private final ContactMemberMapper contactMemberMapper;

    private final ContactListService contactListService;

    private final MailchimpClient mailchimpClient;

    @Override
    public List<Contact> addOrUpdateAudienceMembers() {
        final List<Member> members = new ArrayList<>();
        final List<Contact> contacts = this.contactListService.fetchAllContacts();
        contacts.forEach(contact ->
            updateAudienceMember(contact).ifPresent(members::add)
        );
        return this.contactMemberMapper.mapAsList(members, Contact.class);
    }

    private Optional<Member> updateAudienceMember(final Contact contact) {
        final Member member = this.contactMemberMapper.map(contact, Member.class);
        final Member updatedMember = this.mailchimpClient.updateAudienceMember(audienceId, member.getEmailAddress(), member);
        log.debug("Member {} updated successfully", updatedMember.getEmailAddress());
        return Optional.of(updatedMember); // TODO manage exceptions
    }
}
