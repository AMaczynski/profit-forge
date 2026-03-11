package com.amaczynski.model;


import com.amaczynski.events.PartyRelationshipAdded;

public record PartyRelationship(PartyRelationshipId id, PartyRole from, PartyRole to, RelationshipName name) {

    public static PartyRelationship from(PartyRelationshipId id, PartyRole from, PartyRole to, RelationshipName name) {
        return new PartyRelationship(id, from, to, name);
    }

    public PartyRelationshipAdded toPartyRelationshipAddedEvent() {
        return new PartyRelationshipAdded(id.asString(), from.partyId().asString(),
                from.role().asString(), to.partyId().asString(), to.role().asString(), name.asString());
    }
}
