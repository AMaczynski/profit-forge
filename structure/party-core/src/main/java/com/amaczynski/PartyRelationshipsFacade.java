package com.amaczynski;

import com.amaczynski.common.Result;
import com.amaczynski.events.PartyRelationshipDefinitionFailed;
import com.amaczynski.factory.PartyRelationshipFactory;
import com.amaczynski.factory.PartyRoleFactory;
import com.amaczynski.model.*;
import com.amaczynski.repository.PartyRelationshipRepository;
import com.amaczynski.repository.PartyRepository;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import com.amaczynski.common.Pair;

import java.util.function.BiFunction;

@AllArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE, makeFinal = true)
public class PartyRelationshipsFacade {

    static BiFunction<PartyRelationshipDefinitionFailed, PartyRelationshipDefinitionFailed, PartyRelationshipDefinitionFailed> ANY_FAILURE = (fromFailure, toFailure) -> fromFailure != null ? fromFailure : toFailure;

    PartyRepository partyRepository;
    PartyRelationshipRepository repository;
    PartyRoleFactory partyRoleFactory;
    PartyRelationshipFactory partyRelationshipFactory;
    EventPublisher eventPublisher;


    public Result<PartyRelationshipDefinitionFailed, PartyRelationship> assign(PartyId fromId, Role fromRole, PartyId toId, Role toRole, RelationshipName name) {

        Result<PartyRelationshipDefinitionFailed, PartyRole> fromParty = definePartyRoleFor(fromId, fromRole);
        Result<PartyRelationshipDefinitionFailed, PartyRole> toParty = definePartyRoleFor(toId, toRole);

        return fromParty.combine(toParty, ANY_FAILURE, Pair::of)
                .flatMap(rolesPair -> partyRelationshipFactory.defineFor(rolesPair.first(), rolesPair.second(), name))
                //we should handle unique key (from, fromRole, to, toRole, relName) constraint violations here
                .peekSuccess(repository::save)
                .peekSuccess(relation -> eventPublisher.publish(relation.toPartyRelationshipAddedEvent()));
    }


    private Result<PartyRelationshipDefinitionFailed, PartyRole> definePartyRoleFor(PartyId toId, Role toRole) {
        return partyRepository.findBy(toId)
                .map(party -> partyRoleFactory.defineFor(party, toRole))
                .map(party -> party.mapFailure(failure -> PartyRelationshipDefinitionFailed.dueTo(failure.reason())))
                .orElse(Result.failure(new PartyRelationshipDefinitionFailed("PARTY_NOT_FOUND")));
    }
}