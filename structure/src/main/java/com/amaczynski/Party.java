package com.amaczynski;

import java.util.Objects;

public abstract class Party {

    private final PartyId partyId;

    public Party(PartyId partyId) {
        if (partyId == null) {
            throw new IllegalArgumentException("Party Id value cannot be null");
        }
        this.partyId = partyId;
    }

    public PartyId id() {
        return partyId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Party party)) {
            return false;
        }
        return Objects.equals(partyId, party.partyId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(partyId);
    }
}