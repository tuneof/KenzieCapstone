package com.kenzie.appserver.service;

import com.kenzie.appserver.repositories.FreelancerRepository;
import com.kenzie.appserver.service.model.Freelancer;
import com.kenzie.capstone.service.client.LambdaServiceClient;

import java.util.List;

public class FreelancerService {
    private FreelancerRepository freelancerRepository;

    private LambdaServiceClient lambdaServiceClient;

    public FreelancerService(FreelancerRepository repository, LambdaServiceClient client){
        this.freelancerRepository = repository;
        this.lambdaServiceClient = client;
    }

    public Freelancer findById(String id){
        return null;
    }

    public List<Freelancer> findAll(){
        return null;
    }

    public Freelancer addNewFreelancer(Freelancer freelancer){
        return null;
    }

    public Freelancer updateFreelancer(String id){
        return null;
    }
}
