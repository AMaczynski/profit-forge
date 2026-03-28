# structure

Domain model for organizational party structures.

## Purpose

This module provides a generic, pluggable model for representing **parties** (people,
organizations, or any actor) and the **relationships** between them. It is based on the
**Party-Role-Relationship** pattern (also known as the Accountability Pattern).

The model intentionally makes no assumptions about specific business roles or relationship
types — those are passed as plain strings, keeping this layer domain-agnostic.

This model was inspired by and created on top of ideas from the
[Software-Archetypes/archetypes](https://github.com/Software-Archetypes/archetypes)
project, which provides a strong conceptual foundation for modeling roles, relationships,
and organizational structures.
w
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

## Module structure — Ports & Adapters

This project is structured as a **Ports & Adapters** (Hexagonal) architecture so that
the core domain can be dropped into any project regardless of the underlying technology
(database, ORM, messaging system, HTTP framework, etc.).

```
structure/
├── party-core        ← the portable library (no infrastructure dependencies)
├── party-jpa         ← JPA/Liquibase adapter (one possible persistence implementation)
└── party-sample      ← example: insurance company hierarchy built on top of the library
```

### party-core

The only module consumers **must** depend on. Contains:

- **Domain model** — `Party`, `PartyRole`, `PartyRelationship` and all value objects
- **Ports** — `PartyRepository` and `PartyRelationshipRepository` are plain Java interfaces;
  the core has no opinion on how they are implemented
- **Domain services** — `PartyRelationshipsFacade`, factories, domain events
- **Utilities** — `Result<F,S>` (railway-oriented error handling), `Pair<A,B>`

Dependencies: **Lombok only**.

### party-jpa

An optional adapter that implements the ports using Spring Data JPA. Provides:

- JPA entities (`PartyEntity`, `PartyRelationshipEntity`) with `SINGLE_TABLE` inheritance
- Spring Data repository interfaces (`JpaPartyRepository`, `JpaPartyRelationshipRepository`)
- Adapter implementations (`PartyRepositoryImpl`, `PartyRelationshipRepositoryImpl`)
- `PartyEntityConverter` — a plug-in interface for mapping concrete `Party` subtypes to
  their JPA entities (new subtypes register their own converter; no changes to the adapter)
- Liquibase changesets for the base `parties` and `party_relationships` tables
  (`db/changelog/db.changelog-party-jpa.yaml` — include this from your project's master
  changelog)

Dependencies: `party-core`, `spring-data-jpa`, `jakarta.persistence-api`, `liquibase-core`.

### party-sample

Shows how a real project uses the library. Contains the insurance company hierarchy
(`Employee → SeniorManager / RegionalManager / InsuranceAdvisor`), their JPA entities,
converters, and a Liquibase master changelog that extends the base tables with
employee-specific columns (`name`, `email`).

This module is **not part of the library** — it lives here as a reference implementation
and sandbox.

Dependencies: `party-core`, `party-jpa`, `spring-data-jpa`, `jakarta.persistence-api`.

### Adding a new adapter

Create a new Gradle module (e.g. `party-kafka`, `party-rest`) that depends on
`party-core` only, then implement or consume the relevant port interfaces.
Adapter modules must not depend on each other.

```
party-core          ← always
    ├── party-jpa   ← optional persistence adapter
    ├── party-rest  ← optional HTTP adapter (future)
    └── party-kafka ← optional messaging adapter (future)
```

### Adding a new Party subtype

1. **Domain** (`party-core` or your own project): extend `Party` (or an intermediate
   abstract class like `Employee`).
2. **JPA** (`party-jpa` or your own project): extend `PartyEntity`, add
   `@DiscriminatorValue`, implement `toDomain()`.
3. **Converter**: implement `PartyEntityConverter` and register it with
   `PartyRepositoryImpl`.
4. **Liquibase**: add a changeset for any new columns your subtype requires.

No existing code in `party-core` or `party-jpa` needs to change.

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
