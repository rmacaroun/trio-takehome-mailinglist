package com.trio.rmacaroun.takehome.mailinglist.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Member implements Serializable {

    private static final long serialVersionUID = -7276334294132727437L;

    @JsonProperty("email_address")
    private String emailAddress;

    private String status;

    @JsonProperty("merge_fields")
    private MergeFields mergeFields;
}
