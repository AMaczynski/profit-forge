package com.amaczynski.impl;

import com.amaczynski.model.InsuranceAdvisor;
import com.amaczynski.model.Party;

public class InsuranceAdvisorConverter implements PartyEntityConverter {

    @Override
    public boolean supports(Party party) {
        return party instanceof InsuranceAdvisor;
    }

    @Override
    public PartyEntity toEntity(Party party) {
        InsuranceAdvisor advisor = (InsuranceAdvisor) party;
        var entity = new InsuranceAdvisorEntity();
        entity.setPartyId(advisor.id().value());
        entity.setName(advisor.name());
        entity.setEmail(advisor.email());
        return entity;
    }
}
