package com.amaczynski.impl;

import com.amaczynski.model.PartyId;
import com.amaczynski.model.SeniorManager;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@Entity
@DiscriminatorValue("SENIOR_MANAGER")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SeniorManagerEntity extends EmployeeEntity {

    @Override
    public SeniorManager toDomain() {
        return new SeniorManager(PartyId.of(getPartyId()), getName(), getEmail());
    }
}
