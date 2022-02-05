package com.trio.rmacaroun.takehome.mailinglist.integration;

import com.trio.rmacaroun.takehome.mailinglist.client.MailchimpClient;
import com.trio.rmacaroun.takehome.mailinglist.dto.Contact;
import com.trio.rmacaroun.takehome.mailinglist.dto.Member;
import com.trio.rmacaroun.takehome.mailinglist.dto.MergeFields;
import com.trio.rmacaroun.takehome.mailinglist.dto.Status;
import com.trio.rmacaroun.takehome.mailinglist.service.ContactListService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;

import java.util.Arrays;
import java.util.Date;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.anyString;
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
        Mockito.when(this.contactListService.listAllContacts()).thenReturn(Arrays.asList(this.contact));
        Mockito.when(this.mailchimpClient.updateAudienceMember(anyString(), anyString(), Mockito.any())).thenReturn(this.member);
        this.mockMvc.perform(
                        get("/contacts/sync"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpectAll(
                        new ResultMatcher[]{
                                jsonPath("$.syncedContacts", is(1)),
                                jsonPath("$.contacts", hasSize(1)),
                                jsonPath("$.contacts[0].email", is("rmacaroun@hotmail.com"))
                        }
                );
    }
}
