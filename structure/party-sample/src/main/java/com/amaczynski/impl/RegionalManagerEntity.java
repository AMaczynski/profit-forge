package com.amaczynski.impl;

import com.amaczynski.model.PartyId;
import com.amaczynski.model.RegionalManager;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@Entity
@DiscriminatorValue("REGIONAL_MANAGER")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RegionalManagerEntity extends EmployeeEntity {

    @Override
    public RegionalManager toDomain() {
        return new RegionalManager(PartyId.of(getPartyId()), getName(), getEmail());
    }
}
