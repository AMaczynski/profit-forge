package com.amaczynski.impl;

import com.amaczynski.model.Party;
import com.amaczynski.model.SeniorManager;

public class SeniorManagerConverter implements PartyEntityConverter {

    @Override
    public boolean supports(Party party) {
        return party instanceof SeniorManager;
    }

    @Override
    public PartyEntity toEntity(Party party) {
        SeniorManager manager = (SeniorManager) party;
        var entity = new SeniorManagerEntity();
        entity.setPartyId(manager.id().value());
        entity.setName(manager.name());
        entity.setEmail(manager.email());
        return entity;
    }
}
