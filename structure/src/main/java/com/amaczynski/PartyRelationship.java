package com.amaczynski;


public record PartyRelationship(PartyRelationshipId id, PartyRole from, PartyRole to, RelationshipName name) {

    static PartyRelationship from(PartyRelationshipId id, PartyRole from, PartyRole to, RelationshipName name) {
        return new PartyRelationship(id, from, to, name);
    }

}
