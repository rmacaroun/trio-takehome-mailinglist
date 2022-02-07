package com.trio.rmacaroun.takehome.mailinglist.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
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
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Contact implements Serializable {

    private static final long serialVersionUID = 4278011221288128334L;

    private Integer id;

    private String firstName;

    private String lastName;

    private String email;

    private String avatar;

    private Date createdAt;
}
