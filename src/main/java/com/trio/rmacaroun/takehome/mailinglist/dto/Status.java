package com.trio.rmacaroun.takehome.mailinglist.dto;

public enum Status {

    SUBSCRIBED("subscribed"),
    UNSUBSCRIBED("unsubscribed"),
    CLEANED("cleaned"),
    PENDING("pending"),
    TRANSACTIONAL("transactional");

    private final String value;

    Status(final String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }
}
