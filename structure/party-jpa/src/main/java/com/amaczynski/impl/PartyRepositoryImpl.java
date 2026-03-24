package com.amaczynski.impl;

import com.amaczynski.model.Party;
import com.amaczynski.model.PartyId;
import com.amaczynski.repository.PartyRepository;

import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

public class PartyRepositoryImpl implements PartyRepository {

    private final JpaPartyRepository jpaPartyRepository;
    private final List<PartyEntityConverter> converters;

    public PartyRepositoryImpl(JpaPartyRepository jpaPartyRepository, List<PartyEntityConverter> converters) {
        this.jpaPartyRepository = jpaPartyRepository;
        this.converters = converters;
    }

    @Override
    public Optional<Party> findBy(PartyId partyId) {
        return jpaPartyRepository.findById(partyId.value())
                .map(PartyEntity::toDomain);
    }

    @Override
    public Optional<Party> findBy(PartyId partyId, Class<? extends Party> partyType) {
        return findBy(partyId)
                .filter(partyType::isInstance);
    }

    @Override
    public void save(Party party) {
        PartyEntity entity = converters.stream()
                .filter(c -> c.supports(party))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("No converter registered for party type: " + party.getClass().getName()))
                .toEntity(party);
        jpaPartyRepository.save(entity);
    }

    @Override
    public void delete(PartyId partyId) {
        jpaPartyRepository.deleteById(partyId.value());
    }

    @Override
    public List<Party> findMatching(Predicate<Party> predicate) {
        return jpaPartyRepository.findAll().stream()
                .map(PartyEntity::toDomain)
                .filter(predicate)
                .toList();
    }
}
