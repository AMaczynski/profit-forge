package com.amaczynski.events;

import com.amaczynski.PublishedEvent;

public record PartyRelationshipAdded(String partyRelationshipId, String fromPartyId, String fromPartyRole, String toPartyId, String toPartyRole,
                                     String relationshipName) implements PartyRelatedEvent, PublishedEvent {

}
