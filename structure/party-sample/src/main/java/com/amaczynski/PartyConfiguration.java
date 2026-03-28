package com.amaczynski;

import com.amaczynski.factory.PartyRelationshipFactory;
import com.amaczynski.factory.PartyRoleFactory;
import com.amaczynski.impl.*;
import com.amaczynski.model.PartyRelationshipId;
import com.amaczynski.repository.PartyRelationshipRepository;
import com.amaczynski.repository.PartyRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class PartyConfiguration {

    @Bean
    SeniorManagerConverter seniorManagerConverter() {
        return new SeniorManagerConverter();
    }

    @Bean
    RegionalManagerConverter regionalManagerConverter() {
        return new RegionalManagerConverter();
    }

    @Bean
    InsuranceAdvisorConverter insuranceAdvisorConverter() {
        return new InsuranceAdvisorConverter();
    }

    @Bean
    PartyRepository partyRepository(JpaPartyRepository jpaPartyRepository, List<PartyEntityConverter> converters) {
        return new PartyRepositoryImpl(jpaPartyRepository, converters);
    }

    @Bean
    PartyRelationshipRepository partyRelationshipRepository(JpaPartyRelationshipRepository jpaPartyRelationshipRepository) {
        return new PartyRelationshipRepositoryImpl(jpaPartyRelationshipRepository);
    }

    @Bean
    PartyRoleFactory partyRoleFactory() {
        return new PartyRoleFactory();
    }

    @Bean
    PartyRelationshipFactory partyRelationshipFactory() {
        return new PartyRelationshipFactory(PartyRelationshipId::random);
    }

    @Bean
    EventPublisher eventPublisher() {
        return event -> {};
    }

    @Bean
    PartyRelationshipsFacade partyRelationshipsFacade(
            PartyRepository partyRepository,
            PartyRelationshipRepository partyRelationshipRepository,
            PartyRoleFactory partyRoleFactory,
            PartyRelationshipFactory partyRelationshipFactory,
            EventPublisher eventPublisher) {
        return new PartyRelationshipsFacade(
                partyRepository,
                partyRelationshipRepository,
                partyRoleFactory,
                partyRelationshipFactory,
                eventPublisher
        );
    }
}
