package com.amaczynski.impl;

import com.amaczynski.model.Party;

public interface PartyEntityConverter {

    boolean supports(Party party);

    PartyEntity toEntity(Party party);
}
