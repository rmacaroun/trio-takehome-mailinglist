package com.trio.rmacaroun.takehome.mailinglist.client;

import com.trio.rmacaroun.takehome.mailinglist.config.MailchimpConfig;
import com.trio.rmacaroun.takehome.mailinglist.dto.Member;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

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
}
