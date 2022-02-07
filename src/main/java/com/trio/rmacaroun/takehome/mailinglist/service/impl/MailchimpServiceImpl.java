package com.trio.rmacaroun.takehome.mailinglist.service.impl;

import com.trio.rmacaroun.takehome.mailinglist.client.MailchimpClient;
import com.trio.rmacaroun.takehome.mailinglist.dto.Audiences;
import com.trio.rmacaroun.takehome.mailinglist.dto.Audiences.Audience;
import com.trio.rmacaroun.takehome.mailinglist.dto.Contact;
import com.trio.rmacaroun.takehome.mailinglist.dto.Member;
import com.trio.rmacaroun.takehome.mailinglist.mapper.ContactMemberMapper;
import com.trio.rmacaroun.takehome.mailinglist.service.ContactListService;
import com.trio.rmacaroun.takehome.mailinglist.service.MailchimpService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class MailchimpServiceImpl implements MailchimpService {

    private final ContactMemberMapper contactMemberMapper;

    private final ContactListService contactListService;

    private final MailchimpClient mailchimpClient;

    @Override
    public List<Contact> addOrUpdateAudienceMembers() {
        final List<Member> members = new ArrayList<>();
        final List<Contact> contacts = this.contactListService.listAllContacts();
        final String audienceId = this.findAudienceId();
        contacts.forEach(contact ->
                updateAudienceMember(audienceId, contact).ifPresent(members::add)
        );
        return this.contactMemberMapper.mapAsList(members, Contact.class);
    }

    @Override
    public String findAudienceId() {
        final Audiences audiences = this.mailchimpClient.listAudiences();
        String audienceId;
        if (!audiences.getLists().isEmpty()) {
            final Audience audience = audiences.getLists().get(0);
            audienceId = audience.getId();
            if (audiences.getTotalItems() > 1 || audiences.getLists().size() > 1) {
                log.warn("Mailchimp recommends using a single audience/list. https://mailchimp.com/resources/one-audience-organize-contacts-to-optimize-marketing/");
                log.info("Using the first audience/list in the account. Audience Id: {}", audience.getId());
            }
        } else {
            final String errorMessage = "Unable to find the audience.";
            log.error(errorMessage);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, errorMessage);
        }
        return audienceId;
    }

    private Optional<Member> updateAudienceMember(final String audienceId, final Contact contact) {
        final Member member = this.contactMemberMapper.map(contact, Member.class);
        final Member updatedMember = this.mailchimpClient.updateAudienceMember(audienceId, member.getEmailAddress(), member);
        log.debug("Member {} updated successfully", updatedMember.getEmailAddress());
        return Optional.of(updatedMember);
    }
}
