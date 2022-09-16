package com.kenzie.appserver.controller;

import com.kenzie.appserver.controller.model.FreelancerCreateRequest;
import com.kenzie.appserver.controller.model.FreelancerResponse;
import com.kenzie.appserver.controller.model.FreelancerUpdateRequest;
import com.kenzie.appserver.service.FreelancerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/freelancers")
public class FreelancerController {

    private FreelancerService freelancerService;

    FreelancerController(FreelancerService service) {
        this.freelancerService = service;
    }

    @PostMapping
    public ResponseEntity<FreelancerResponse> createFreelancer(@RequestBody FreelancerCreateRequest request) throws Exception {
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<FreelancerResponse> getFreelancerById(@PathVariable("id") String id) throws Exception {
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/all")
    public ResponseEntity<List<FreelancerResponse>> getAllFreelancers() {
        return ResponseEntity.noContent().build();
    }


    @PutMapping
    public ResponseEntity<FreelancerResponse> updateFreelancer(@RequestBody FreelancerUpdateRequest request) {
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity deleteFreelancerById(@PathVariable("id") String id) {
        return ResponseEntity.noContent().build();
    }
}
