package com.amaczynski.model;

public abstract class Employee extends Party {

    private final String name;
    private final String email;

    protected Employee(PartyId partyId, String name, String email) {
        super(partyId);
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Employee name cannot be null or blank");
        }
        if (email == null || email.isBlank()) {
            throw new IllegalArgumentException("Employee email cannot be null or blank");
        }
        this.name = name;
        this.email = email;
    }

    public String name() {
        return name;
    }

    public String email() {
        return email;
    }
}
