package com.trio.rmacaroun.takehome.mailinglist.mapper;

import com.trio.rmacaroun.takehome.mailinglist.dto.Contact;
import com.trio.rmacaroun.takehome.mailinglist.dto.Member;
import com.trio.rmacaroun.takehome.mailinglist.dto.MergeFields;
import com.trio.rmacaroun.takehome.mailinglist.dto.Status;
import ma.glasnost.orika.CustomMapper;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.MappingContext;
import ma.glasnost.orika.impl.ConfigurableMapper;
import org.springframework.stereotype.Component;

@Component
public class ContactMemberMapper extends ConfigurableMapper {

    @Override
    protected void configure(MapperFactory factory) {
        factory.classMap(Contact.class, Member.class)
                // -> -> ->
                .fieldAToB("email", "emailAddress")
                .fieldAToB("firstName", "mergeFields.firstName")
                .fieldAToB("lastName", "mergeFields.lastName")
                // <- <- <-
                .fieldBToA("emailAddress", "email")
                .fieldBToA("mergeFields.firstName", "firstName")
                .fieldBToA("mergeFields.lastName", "lastName")
                .byDefault()
                .customize(new CustomMapper<Contact, Member>() {
                    @Override
                    public void mapAtoB(Contact contact, Member member, MappingContext context) {
                        member.setStatus(Status.SUBSCRIBED.getValue());
                        member.setStatusIfNew(Status.SUBSCRIBED.getValue());
                        MergeFields mergeFields = member.getMergeFields();
                        mergeFields.setAvatar(contact.getAvatar());
                        mergeFields.setCreatedAt(contact.getCreatedAt());
                        mergeFields.setTrioId(contact.getId());
                    }
                })
                .register();
    }
}
