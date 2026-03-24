package com.amaczynski.factory;

import com.amaczynski.common.Result;
import com.amaczynski.events.PartyRelationshipDefinitionFailed;
import com.amaczynski.model.PartyRelationship;
import com.amaczynski.model.PartyRelationshipId;
import com.amaczynski.model.PartyRole;
import com.amaczynski.model.RelationshipName;

import java.util.function.Supplier;


public class PartyRelationshipFactory {

    private final Supplier<PartyRelationshipId> partyRelationshipIdSupplier;

    PartyRelationshipFactory(Supplier<PartyRelationshipId> partyRelationshipIdSupplier) {
        this.partyRelationshipIdSupplier = partyRelationshipIdSupplier != null ? partyRelationshipIdSupplier : PartyRelationshipId::random;
    }

    public Result<PartyRelationshipDefinitionFailed, PartyRelationship> defineFor(PartyRole from, PartyRole to, RelationshipName name) {
            return Result.success(PartyRelationship.from(partyRelationshipIdSupplier.get(), from, to, name));
    }
}
