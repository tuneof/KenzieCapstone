package com.kenzie.appserver.service;

import com.kenzie.appserver.controller.model.FreelancerResponse;
import com.kenzie.appserver.service.model.Freelancer;
import com.kenzie.capstone.service.client.LambdaServiceClient;

import java.util.ArrayList;
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

    public List<Freelancer> findAllFreelancers() {
        List<Freelancer> freelancers = new ArrayList<>();
        freelancerRepository
                .findAll()
                .forEach(freelancer -> freelancers.add(toFreelancer(freelancer)));

        return freelancers;
    }

    public Freelancer addNewFreelancer(Freelancer freelancer){
        return ;
    }

    public Freelancer updateFreelancer(String id){
        return ;
    }

    private Freelancer toFreelancer(FreelancerRecord record) {
        Freelancer freelancer = new Freelancer(record.getId,
                record.getName,
                record.getExpertise,
                record.getRate,
                record.getLocation,
                record.getContact);

        return freelancer;
    }
}
