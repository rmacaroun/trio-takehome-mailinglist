package com.trio.rmacaroun.takehome.mailinglist.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
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
public class Audiences implements Serializable {

    private static final long serialVersionUID = -6367002802836695754L;

    private List<Audience> lists;

    @JsonProperty("total_items")
    private Integer totalItems;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Audience {

        private String id;
    }
}
