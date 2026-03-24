package com.amaczynski.impl;

import com.amaczynski.model.*;
import com.amaczynski.repository.PartyRelationshipRepository;

import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

public class PartyRelationshipRepositoryImpl implements PartyRelationshipRepository {

    private final JpaPartyRelationshipRepository jpaRepository;

    public PartyRelationshipRepositoryImpl(JpaPartyRelationshipRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public List<PartyRelationship> findAllRelationsFrom(PartyId partyId) {
        return jpaRepository.findByFromPartyId(partyId.value()).stream()
                .map(PartyRelationshipEntity::toDomain)
                .toList();
    }

    @Override
    public List<PartyRelationship> findAllRelationsFrom(PartyId partyId, RelationshipName name) {
        return jpaRepository.findByFromPartyIdAndRelationshipName(partyId.value(), name.value()).stream()
                .map(PartyRelationshipEntity::toDomain)
                .toList();
    }

    @Override
    public Optional<PartyRelationship> findBy(PartyRelationshipId relationshipId) {
        return jpaRepository.findById(relationshipId.value())
                .map(PartyRelationshipEntity::toDomain);
    }

    @Override
    public void save(PartyRelationship partyRelationship) {
        jpaRepository.save(PartyRelationshipEntity.from(partyRelationship));
    }

    @Override
    public Optional<PartyRelationshipId> delete(PartyRelationshipId relationshipId) {
        if (!jpaRepository.existsById(relationshipId.value())) {
            return Optional.empty();
        }
        jpaRepository.deleteById(relationshipId.value());
        return Optional.of(relationshipId);
    }

    @Override
    public List<PartyRelationship> findMatching(Predicate<PartyRelationship> predicate) {
        return jpaRepository.findAll().stream()
                .map(PartyRelationshipEntity::toDomain)
                .filter(predicate)
                .toList();
    }
}
