package com.amaczynski.impl;

import com.amaczynski.model.InsuranceAdvisor;
import com.amaczynski.model.PartyId;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@Entity
@DiscriminatorValue("INSURANCE_ADVISOR")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class InsuranceAdvisorEntity extends EmployeeEntity {

    @Override
    public InsuranceAdvisor toDomain() {
        return new InsuranceAdvisor(PartyId.of(getPartyId()), getName(), getEmail());
    }
}
