package com.amaczynski;


public record PartyRole(PartyId partyId, Role role) {

    public PartyRole {
        if (partyId == null) {
            throw new IllegalArgumentException("Party Id value cannot be null");
        }
        if (role == null) {
            throw new IllegalArgumentException("Role value cannot be null");
        }
    }

    static PartyRole of(PartyId partyId, String value) {
        return of(partyId, Role.of(value));
    }

    static PartyRole of(PartyId partyId, Role role) {
        return new PartyRole(partyId, role);
    }

}
