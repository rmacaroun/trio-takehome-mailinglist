package com.trio.rmacaroun.takehome.mailinglist.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Members implements Serializable {

    private static final long serialVersionUID = 7747266518713901958L;

    private List<Member> members;
}
