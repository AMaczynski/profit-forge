package com.amaczynski.model;

import java.util.UUID;


public record PartyId(UUID value) {

    public PartyId {
        if (value == null) {
            throw new IllegalArgumentException("Party Id value cannot be null");
        }
    }

    public String asString() {
        return value.toString();
    }

    public static PartyId of(UUID value) {
        return new PartyId(value);
    }

    public static PartyId random() {
        return of(UUID.randomUUID());
    }
}
