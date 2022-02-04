package com.trio.rmacaroun.takehome.mailinglist.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.trio.rmacaroun.takehome.mailinglist.dto.Contact;
import com.trio.rmacaroun.takehome.mailinglist.dto.Member;
import com.trio.rmacaroun.takehome.mailinglist.mapper.ContactMemberMapper;
import com.trio.rmacaroun.takehome.mailinglist.service.ContactListService;
import com.trio.rmacaroun.takehome.mailinglist.service.MailchimpService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;

import static org.springframework.http.HttpMethod.PUT;

@Service
@Slf4j
@RequiredArgsConstructor
public class MailchimpServiceImpl implements MailchimpService {

    @Value("${application.mailchimp.api.username}")
    private String username;

    @Value("${application.mailchimp.api.baseurl}")
    private String baseUrl;

    @Value("${application.mailchimp.api.key}")
    private String apiKey;

    @Value("${application.mailchimp.api.audience-id}")
    private String audienceId;

    @Value("${application.mailchimp.api.endpoint.add-member}")
    private String addMemberEndpoint; // TODO fix hard-coded

    @Value("${application.mailchimp.api.endpoint.update-member}")
    private String updateMemberEndpoint; // TODO fix hard-coded

    @Value("${application.mailchimp.api.endpoint.search-member}")
    private String searchMemberEndpoint; // TODO fix hard-coded

    private final RestTemplate restTemplate;

    private final ObjectMapper objectMapper;

    private final ContactMemberMapper contactMemberMapper;

    private final ContactListService contactListService;

    @Override
    public List<Contact> addOrUpdateAudienceMembers() {
        final List<Member> members = new ArrayList<>();
        final List<Contact> contacts = this.contactListService.fetchAllContacts();
        contacts.stream().forEach(contact ->
            updateAudienceMember(contact).ifPresent(members::add)
        );
        return this.contactMemberMapper.mapAsList(members, Contact.class);
    }

    private Optional<Member> updateAudienceMember(final Contact contact) {
        final String addMemberUrl = baseUrl + updateMemberEndpoint;
        final Member member = this.contactMemberMapper.map(contact, Member.class);
        final Map<String, String> query = new HashMap<>();
        query.put("subscriber_hash", member.getEmailAddress());
        final ResponseEntity<Member> responseEntity = this.restTemplate.exchange(addMemberUrl, PUT, setupHttpEntity(member), Member.class, query);
        return Optional.ofNullable(responseEntity.getBody());
    }

    private HttpEntity<String> setupHttpEntity(Member member) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setBasicAuth(username, apiKey);
        String body = null;
        try {
            if (member != null) {
                body = this.objectMapper.writeValueAsString(member);
            }
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        final HttpEntity<String> httpEntity = new HttpEntity<>(body, httpHeaders);
        return httpEntity;
    }
}
