package com.kenzie.appserver.service;

import com.kenzie.appserver.controller.model.FreelancerResponse;
import com.kenzie.appserver.service.model.Freelancer;
import com.kenzie.capstone.service.client.LambdaServiceClient;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class FreelancerService {
    private FreelancerRepository freelancerRepository;

    private LambdaServiceClient lambdaServiceClient;

    public FreelancerService(FreelancerRepository repository, LambdaServiceClient client){
        this.freelancerRepository = repository;
        this.lambdaServiceClient = client;
    }

    public FreelancerResponse findFreelancerById(String id){
        return freelancerRepository.findById(id)
                .map(this::toFreelancerResponse)
                .orElse(null);
    }

    public List<FreelancerResponse> findAllFreeanceers() {
        List<FreelancerRecord> records = StreamSupport.stream(freelancerRepository.findAll().spliterator(), true).collect(Collectors.toList());

        return records.stream()
                .map(this::toFreelancerResponse)
                .collect(Collectors.toList());
    }

    public Freelancer addNewFreelancer(Freelancer freelancer){
        return ;
    }

    public Freelancer updateFreelancer(String id){
        return ;
    }

    private FreelancerResponse toFreelancerResponse(FreelancerRecord record) {
        if (record == null) {
            return null;
        }

        FreelancerResponse freelancerResponse = new FreelancerResponse();
        freelancerResponse.setName(record.getName());
        freelancerResponse.setFreelancerId(record.getFreelancerId);
        freelancerResponse.setContact(record.getContact);
        freelancerResponse.setExpertise(record.getExpertise);
        freelancerResponse.setLocation(record.getLocation);
        freelancerResponse.setRate(record.getRate);

        return freelancerResponse;
    }
}
