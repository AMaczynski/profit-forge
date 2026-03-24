package com.amaczynski.impl;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface JpaPartyRelationshipRepository extends JpaRepository<PartyRelationshipEntity, UUID> {

    List<PartyRelationshipEntity> findByFromPartyId(UUID fromPartyId);

    List<PartyRelationshipEntity> findByFromPartyIdAndRelationshipName(UUID fromPartyId, String relationshipName);
}
