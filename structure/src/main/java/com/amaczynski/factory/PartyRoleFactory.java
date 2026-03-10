package com.amaczynski.factory;

import com.amaczynski.common.Result;
import com.amaczynski.events.PartyRoleDefinitionFailed;
import com.amaczynski.model.Party;
import com.amaczynski.model.PartyRole;
import com.amaczynski.model.Role;

public class PartyRoleFactory {

    public Result<PartyRoleDefinitionFailed, PartyRole> defineFor(Party party, Role role) {
        // TODO add policy
        return Result.success(PartyRole.of(party.id(), role));
    }
}
