package com.amaczynski.impl;

import com.amaczynski.model.Party;
import com.amaczynski.model.RegionalManager;

public class RegionalManagerConverter implements PartyEntityConverter {

    @Override
    public boolean supports(Party party) {
        return party instanceof RegionalManager;
    }

    @Override
    public PartyEntity toEntity(Party party) {
        RegionalManager manager = (RegionalManager) party;
        var entity = new RegionalManagerEntity();
        entity.setPartyId(manager.id().value());
        entity.setName(manager.name());
        entity.setEmail(manager.email());
        return entity;
    }
}
