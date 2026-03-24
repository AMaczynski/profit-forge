package com.amaczynski.impl;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface JpaPartyRepository extends JpaRepository<PartyEntity, UUID> {
}
