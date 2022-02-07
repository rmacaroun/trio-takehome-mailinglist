package com.trio.rmacaroun.takehome.mailinglist.integration;

import com.trio.rmacaroun.takehome.mailinglist.client.MailchimpClient;
import com.trio.rmacaroun.takehome.mailinglist.dto.*;
import com.trio.rmacaroun.takehome.mailinglist.service.ContactListService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Date;

import static java.util.Collections.singletonList;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class ContactListControllerIntegTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ContactListService contactListService;

    @MockBean
    private MailchimpClient mailchimpClient;

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
        MergeFields mergeFields = MergeFields.builder()
                .firstName("Rafael")
                .lastName("Macaroun")
                .createdAt(now)
                .avatar("http://localhost:8080/myavatar.jpg")
                .trioId(100)
                .build();
        this.member = Member.builder()
                .emailAddress("rmacaroun@hotmail.com")
                .status(Status.SUBSCRIBED.getValue())
                .mergeFields(
                        mergeFields)
                .build();
    }

    @Test
    public void testSyncContactController() throws Exception {
        MergeFields mergeFields = MergeFields.builder()
                .firstName("Gabriel")
                .lastName("Kugel")
                .createdAt(now)
                .avatar("http://localhost:8080/newavatar.jpg")
                .trioId(100)
                .build();
        Member member = Member.builder()
                .emailAddress("gkugel@gmail.com")
                .status(Status.SUBSCRIBED.getValue())
                .mergeFields(mergeFields)
                .build();
        when(this.contactListService.listAllContacts()).thenReturn(singletonList(this.contact));
        when(this.mailchimpClient.listMembers(anyString(), anyString())).thenReturn(Members.builder().members(singletonList(member)).build());
        when(this.mailchimpClient.archiveMember(anyString(), anyString())).thenReturn(ResponseEntity.status(HttpStatus.NO_CONTENT).build());
        when(this.mailchimpClient.updateAudienceMember(anyString(), anyString(), Mockito.any())).thenReturn(this.member);
        when(this.mailchimpClient.listAudiences()).thenReturn(
                Audiences.builder()
                        .lists(singletonList(Audiences.Audience.builder().id("1").build())).totalItems(1).build()
        );
        this.mockMvc.perform(
                        get("/contacts/sync"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpectAll(
                        jsonPath("$.syncedContacts", is(1)),
                        jsonPath("$.contacts", hasSize(1)),
                        jsonPath("$.contacts[0].email", is("rmacaroun@hotmail.com")));
    }
}
