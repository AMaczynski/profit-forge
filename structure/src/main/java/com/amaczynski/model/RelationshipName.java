package com.amaczynski.model;


public record RelationshipName(String value) {

    public RelationshipName {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("Relationship name cannot be null or blank");
        }
    }

    static RelationshipName of(String value) {
        return new RelationshipName(value);
    }

    String asString() {
        return value;
    }

}
