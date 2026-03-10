package com.amaczynski.model;

import java.util.UUID;


public record PartyRelationshipId(UUID value) {

    public PartyRelationshipId {
        if (value == null) {
            throw new IllegalArgumentException("Party Id value cannot be null");
        }
    }

    public String asString() {
        return value.toString();
    }

    public static PartyRelationshipId of(UUID value) {
        return new PartyRelationshipId(value);
    }

    public static PartyRelationshipId random() {
        return of(UUID.randomUUID());
    }
}
