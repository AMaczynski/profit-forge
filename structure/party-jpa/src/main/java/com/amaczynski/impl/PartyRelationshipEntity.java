package com.amaczynski.impl;

import com.amaczynski.model.*;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity
@Table(name = "party_relationships")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PartyRelationshipEntity {

    @Id
    @Column(nullable = false)
    private UUID id;

    @Column(nullable = false)
    private UUID fromPartyId;

    @Column(nullable = false)
    private String fromRole;

    @Column(nullable = false)
    private UUID toPartyId;

    @Column(nullable = false)
    private String toRole;

    @Column(nullable = false)
    private String relationshipName;

    public PartyRelationship toDomain() {
        return PartyRelationship.from(
                new PartyRelationshipId(id),
                PartyRole.of(PartyId.of(fromPartyId), fromRole),
                PartyRole.of(PartyId.of(toPartyId), toRole),
                new RelationshipName(relationshipName)
        );
    }

    public static PartyRelationshipEntity from(PartyRelationship relationship) {
        var entity = new PartyRelationshipEntity();
        entity.id = relationship.id().value();
        entity.fromPartyId = relationship.from().partyId().value();
        entity.fromRole = relationship.from().role().name();
        entity.toPartyId = relationship.to().partyId().value();
        entity.toRole = relationship.to().role().name();
        entity.relationshipName = relationship.name().value();
        return entity;
    }
}
