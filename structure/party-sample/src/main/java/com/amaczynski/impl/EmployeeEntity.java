package com.amaczynski.impl;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class EmployeeEntity extends PartyEntity {

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String email;
}
