package com.trio.rmacaroun.takehome.mailinglist.client;

import com.trio.rmacaroun.takehome.mailinglist.config.MailchimpConfig;
import com.trio.rmacaroun.takehome.mailinglist.dto.Audiences;
import com.trio.rmacaroun.takehome.mailinglist.dto.Member;
import com.trio.rmacaroun.takehome.mailinglist.dto.Members;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@FeignClient(
        value = "mailchimp-client",
        url = "${application.mailchimp.api.baseurl}",
        configuration = MailchimpConfig.class
)
public interface MailchimpClient {

    @PutMapping(path = "${application.mailchimp.api.endpoint.update-member}")
    Member updateAudienceMember(final @PathVariable("audience_id") String audienceId,
                                final @PathVariable("subscriber_hash") String subscriberHash,
                                final @RequestBody Member member);

    @GetMapping(path = "${application.mailchimp.api.endpoint.list-audience}")
    Audiences listAudiences();

    @GetMapping(path = "${application.mailchimp.api.endpoint.list-members}")
    Members listMembers(final @PathVariable("audience_id") String audienceId,
                        final @RequestParam("status") String status);

    @DeleteMapping(path = "${application.mailchimp.api.endpoint.archive-member}")
    ResponseEntity<?> archiveMember(final @PathVariable("audience_id") String audienceId,
                                    final @PathVariable("subscriber_hash") String subscriberHash);
}
