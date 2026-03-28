package com.amaczynski.api.dto;

import com.amaczynski.model.PartyRelationship;

public record PartyRelationshipResponse(
        String id,
        String fromPartyId,
        String fromRole,
        String toPartyId,
        String toRole,
        String relationshipName
) {

    public static PartyRelationshipResponse from(PartyRelationship relationship) {
        return new PartyRelationshipResponse(
                relationship.id().asString(),
                relationship.from().partyId().asString(),
                relationship.from().role().name(),
                relationship.to().partyId().asString(),
                relationship.to().role().name(),
                relationship.name().value()
        );
    }
}
