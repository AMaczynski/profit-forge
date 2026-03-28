package com.amaczynski.api;

import com.amaczynski.PartyRelationshipsFacade;
import com.amaczynski.api.dto.AssignRelationshipRequest;
import com.amaczynski.api.dto.PartyRelationshipResponse;
import com.amaczynski.model.*;
import com.amaczynski.repository.PartyRelationshipRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/relationships")
@AllArgsConstructor
public class PartyRelationshipController {

    private final PartyRelationshipsFacade facade;
    private final PartyRelationshipRepository repository;

    @PostMapping
    public ResponseEntity<?> assign(@RequestBody AssignRelationshipRequest request) {
        var result = facade.assign(
                PartyId.of(UUID.fromString(request.fromPartyId())),
                new Role(request.fromRole()),
                PartyId.of(UUID.fromString(request.toPartyId())),
                new Role(request.toRole()),
                new RelationshipName(request.relationshipName())
        );

        return result.ifSuccessOrElse(
                relationship -> ResponseEntity
                        .created(URI.create("/api/relationships/" + relationship.id().asString()))
                        .body(PartyRelationshipResponse.from(relationship)),
                failure -> ResponseEntity.badRequest().body(failure.reason())
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<PartyRelationshipResponse> getById(@PathVariable UUID id) {
        return repository.findBy(new PartyRelationshipId(id))
                .map(rel -> ResponseEntity.ok(PartyRelationshipResponse.from(rel)))
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public List<PartyRelationshipResponse> getFromParty(@RequestParam UUID fromPartyId) {
        return repository.findAllRelationsFrom(PartyId.of(fromPartyId)).stream()
                .map(PartyRelationshipResponse::from)
                .toList();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        if (repository.delete(new PartyRelationshipId(id)).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }
}
