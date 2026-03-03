package com.amaczynski;


public record Role(String name) {

    public Role {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Role name cannot be null or blank");
        }
    }

    static Role of(String value) {
        return new Role(value);
    }

    String asString() {
        return name;
    }
}
