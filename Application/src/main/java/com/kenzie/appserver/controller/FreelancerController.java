package com.kenzie.appserver.controller;

import com.kenzie.appserver.controller.model.FreelancerCreateRequest;
import com.kenzie.appserver.controller.model.FreelancerResponse;
import com.kenzie.appserver.controller.model.FreelancerUpdateRequest;
import com.kenzie.appserver.service.FreelancerService;
import com.kenzie.appserver.service.model.Freelancer;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

import static java.util.UUID.randomUUID;

@RestController
@RequestMapping("/Freelancers")
public class FreelancerController {

    private FreelancerService freelancerService;

    FreelancerController(FreelancerService service) {
        this.freelancerService = service;
    }

    @PostMapping
    public ResponseEntity<FreelancerResponse> createFreelancer(@RequestBody FreelancerCreateRequest request) {
        Freelancer freelancer = new Freelancer(randomUUID().toString(), request.getName(), request.getExpertise(),
                request.getRate(), request.getLocation(), request.getContact());

        try {
            freelancerService.addNewFreelancer(freelancer);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        }

        FreelancerResponse response = freelancerToResponse(freelancer);

        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/{freelancerId}")
    public ResponseEntity<FreelancerResponse> getFreelancerById(@PathVariable("freelancerId") String freelancerId) throws Exception {
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<FreelancerResponse>> getAllFreelancers() {
        List<Freelancer> freelancers = freelancerService.findAll();

        if (freelancers == null || freelancers.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        List<FreelancerResponse> responses = freelancers.stream()
                .map(freelancer -> freelancerToResponse(freelancer))
                .collect(Collectors.toList());

        return ResponseEntity.ok(responses);
    }


    @PutMapping
    public ResponseEntity<FreelancerResponse> updateFreelancer(@RequestBody FreelancerUpdateRequest request) {
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{freelancerId}")
    public ResponseEntity deleteFreelancerById(@PathVariable("freelancerId") String freelancerId) {
        return ResponseEntity.noContent().build();
    }
}
