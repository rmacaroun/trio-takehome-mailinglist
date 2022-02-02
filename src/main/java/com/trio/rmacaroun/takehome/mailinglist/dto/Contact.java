package com.trio.rmacaroun.takehome.mailinglist.dto;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@Builder
public class Contact implements Serializable {

    private static final long serialVersionUID = 4278011221288128334L;

    private Long id;

    private String firstName;

    private String lastName;

    private String email;

    private String avatar;

    private Date createdAt;
}
