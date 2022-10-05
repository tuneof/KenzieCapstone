package com.kenzie.appserver.controller;

import com.kenzie.appserver.controller.model.*;
import com.kenzie.appserver.service.FreelancerService;
import com.kenzie.appserver.service.model.Freelancer;
import com.kenzie.capstone.service.model.HireStatus;
import org.springframework.http.HttpStatus;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static java.util.UUID.randomUUID;

@RestController
@RequestMapping("/freelancers")
public class FreelancerController {

    private FreelancerService freelancerService;

    FreelancerController(FreelancerService freelancerService) {
        this.freelancerService = freelancerService;
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

    @GetMapping("/{id}")
    public ResponseEntity<FreelancerResponse> getFreelancerById(@PathVariable("id") String id) throws Exception {
        Freelancer freelancer = freelancerService.findById(id);
        FreelancerResponse freelancerResponse = freelancerToResponse(freelancer);
        return ResponseEntity.ok(freelancerResponse);
    }

    @GetMapping
    public ResponseEntity<List<FreelancerResponse>> getAllFreelancers() {
        List<Freelancer> freelancers = freelancerService.findAll();

        if (freelancers == null || freelancers.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        List<FreelancerResponse> responses = freelancers.stream()
                .map(this::freelancerToResponse)
                .collect(Collectors.toList());

        return ResponseEntity.ok(responses);
    }


    @PutMapping("/{id}")
    public ResponseEntity<FreelancerResponse> updateFreelancer(@RequestBody FreelancerUpdateRequest request) {
        //if the freelancer that is being updated doesn't exist, returns 204
        if (freelancerService.findById(request.getId()) == null) {
            return ResponseEntity.noContent().build();
        }

        //Otherwise, continue executing method
        Freelancer freelancer = new Freelancer(request.getId(),
                request.getName(),
                request.getExpertise(),
                request.getRate(),
                request.getLocation(),
                request.getContact());
        freelancerService.updateFreelancer(freelancer);

        FreelancerResponse response = freelancerToResponse(freelancer);

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("{id}")
    public ResponseEntity deleteFreelancerById(@PathVariable("id") String id) {
        if (freelancerService.findById(id) == null) {
            return ResponseEntity.noContent().build();
        }
        freelancerService.deleteFreelancer(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}/hirestatus")
    public ResponseEntity<HireStatusResponse> getHireStatus(@PathVariable("id") String id) {
        HireStatusResponse hireStatusResponse = new HireStatusResponse();
        hireStatusResponse.setStatus(freelancerService.getFreelancerHireStatus(id));
        return ResponseEntity.ok(hireStatusResponse);
    }

    @PostMapping("/{id}/hirestatus")
    public ResponseEntity<HireStatusResponse> updateHireStatus(@RequestBody HireStatusUpdateRequest request) {
        HireStatusResponse hireStatusResponse = new HireStatusResponse();
        freelancerService.updateFreelancerHireStatus(request.getId(), request.getStatus());
        hireStatusResponse.setStatus(request.getStatus());
        return ResponseEntity.ok(hireStatusResponse);
    }

    private FreelancerResponse freelancerToResponse(Freelancer freelancer) {
        FreelancerResponse freelancerResponse = new FreelancerResponse();
        freelancerResponse.setId(freelancer.getId());
        freelancerResponse.setName(freelancer.getName());
        freelancerResponse.setExpertise(freelancer.getExpertise());
        freelancerResponse.setContact(freelancer.getContact());
        freelancerResponse.setLocation(freelancer.getLocation());
        freelancerResponse.setRate(freelancer.getRate());

        return freelancerResponse;
    }
}