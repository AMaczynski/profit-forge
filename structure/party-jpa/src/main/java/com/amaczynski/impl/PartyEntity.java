package com.amaczynski.impl;

import com.amaczynski.model.Party;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "party_type")
@Table(name = "parties")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class PartyEntity {

    @Id
    @Column(name = "party_id", nullable = false)
    private UUID partyId;

    public abstract Party toDomain();
}
