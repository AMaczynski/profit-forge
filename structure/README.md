# structure

Domain model for organizational party structures.

## Purpose

This module provides a generic, pluggable model for representing **parties** (people,
organizations, or any actor) and the **relationships** between them. It is based on the
**Party-Role-Relationship** pattern (also known as the Accountability Pattern).

The model intentionally makes no assumptions about specific business roles or relationship
types — those are passed as plain strings, keeping this layer domain-agnostic.

## Data Model

### Core classes

| Class | Type | Description |
|---|---|---|
| `Party` | abstract class | Base entity for any actor. Identity is based solely on `PartyId`. |
| `PartyId` | record (value object) | UUID-backed identity for a `Party`. |
| `Role` | record (value object) | Named role a party can play (e.g. `"Broker"`, `"Client"`). |
| `PartyRole` | record (value object) | Combination of a `PartyId` and a `Role` — "Party X playing Role Y". |
| `RelationshipName` | record (value object) | Named label for the type of relationship (e.g. `"manages"`, `"employs"`). |
| `PartyRelationshipId` | record (value object) | UUID-backed identity for a `PartyRelationship`. |
| `PartyRelationship` | record | A directed, named link between two `PartyRole`s. |

### Relationships between classes

```
PartyId ──────────────── Party (abstract)
  │                        │
  │                        └── equality / identity based on PartyId
  │
  └──────────── PartyRole ────────── Role
                    │
         ┌──────────┴──────────┐
         │                     │
       (from)                (to)
         └──────────┬──────────┘
              PartyRelationship
               ├── PartyRelationshipId
               └── RelationshipName
```

A `PartyRelationship` is **directed**: it goes *from* one `PartyRole` *to* another,
and is labelled with a `RelationshipName`.

### Example

```
Alice (as "Broker")  --[manages]--> Bob (as "Client")
```

Maps to:

```java
PartyRole from = PartyRole.of(alice.id(), Role.of("Broker"));
PartyRole to   = PartyRole.of(bob.id(),   Role.of("Client"));
PartyRelationship rel = PartyRelationship.from(
    PartyRelationshipId.random(), from, to, RelationshipName.of("manages")
);
```

## Design notes

- All value objects (`PartyId`, `Role`, `PartyRole`, `RelationshipName`,
  `PartyRelationshipId`) are **immutable Java records** with null/blank guards in their
  compact constructors.
- `Party` is abstract so that concrete subtypes (e.g. `Person`, `Organization`) can be
  introduced without changing the relationship model.
- `Party` equality is identity-based (`PartyId`), not structural — two `Party` instances
  with the same `PartyId` are considered the same party regardless of subtype details.
- Roles and relationship names are untyped strings by design, keeping the model
  generic and reusable across different business contexts.
