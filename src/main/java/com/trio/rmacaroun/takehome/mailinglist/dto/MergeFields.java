package com.trio.rmacaroun.takehome.mailinglist.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MergeFields implements Serializable {

    private static final long serialVersionUID = -280838221284411432L;

    @JsonProperty("FNAME")
    private String firstName;

    @JsonProperty("LNAME")
    private String lastName;

    private Integer trioId;

    private Date createdAt;

    @JsonProperty("AVATAR")
    private String avatar;
}
