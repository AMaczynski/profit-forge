package com.amaczynski.api;

import com.amaczynski.api.dto.CreateEmployeeRequest;
import com.amaczynski.api.dto.EmployeeResponse;
import com.amaczynski.model.*;
import com.amaczynski.repository.PartyRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.UUID;

@RestController
@RequestMapping("/api/parties")
@AllArgsConstructor
public class PartyController {

    private final PartyRepository partyRepository;

    @PostMapping("/senior-managers")
    public ResponseEntity<EmployeeResponse> createSeniorManager(@RequestBody CreateEmployeeRequest request) {
        var party = new SeniorManager(PartyId.random(), request.name(), request.email());
        partyRepository.save(party);
        return created(party);
    }

    @PostMapping("/regional-managers")
    public ResponseEntity<EmployeeResponse> createRegionalManager(@RequestBody CreateEmployeeRequest request) {
        var party = new RegionalManager(PartyId.random(), request.name(), request.email());
        partyRepository.save(party);
        return created(party);
    }

    @PostMapping("/insurance-advisors")
    public ResponseEntity<EmployeeResponse> createInsuranceAdvisor(@RequestBody CreateEmployeeRequest request) {
        var party = new InsuranceAdvisor(PartyId.random(), request.name(), request.email());
        partyRepository.save(party);
        return created(party);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmployeeResponse> getParty(@PathVariable UUID id) {
        return partyRepository.findBy(PartyId.of(id))
                .filter(party -> party instanceof Employee)
                .map(party -> ResponseEntity.ok(EmployeeResponse.from((Employee) party)))
                .orElse(ResponseEntity.notFound().build());
    }

    private ResponseEntity<EmployeeResponse> created(Employee employee) {
        var response = EmployeeResponse.from(employee);
        return ResponseEntity
                .created(URI.create("/api/parties/" + response.id()))
                .body(response);
    }
}
