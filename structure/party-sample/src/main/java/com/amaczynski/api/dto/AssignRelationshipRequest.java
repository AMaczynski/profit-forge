package com.amaczynski.api.dto;

public record AssignRelationshipRequest(
        String fromPartyId,
        String fromRole,
        String toPartyId,
        String toRole,
        String relationshipName
) {
}
