package com.kenzie.appserver.service;

import com.kenzie.appserver.repositories.FreelancerRepository;
import com.kenzie.appserver.service.model.Freelancer;
import com.kenzie.capstone.service.client.LambdaServiceClient;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

public class FreelancerService {
    private FreelancerRepository freelancerRepository;

    private LambdaServiceClient lambdaServiceClient;

    public FreelancerService(FreelancerRepository repository, LambdaServiceClient client){
        this.freelancerRepository = repository;
        this.lambdaServiceClient = client;
    }

    public List<Freelancer> findAll() {
        List<Freelancer> freelancers = new ArrayList<>();
        freelancerRepository
                .findAll()
                .forEach(freelancer -> freelancers.add(toFreelancer(freelancer)));

        return freelancers;
    }

    public Freelancer addNewFreelancer(Freelancer freelancer) {
        FreelancerRecord freelancerRecord = new FreelancerRecord();
        freelancerRecord.setId(freelancer.getId());
        freelancerRecord.setCreatedAt(ZonedDateTime.now());
        freelancerRecord.setModifiedAt(ZonedDateTime.now());
        freelancerRecord.setContact(freelancer.getContact());
        freelancerRecord.setExpertise(freelancer.getExpertise());
        freelancerRecord.setName(freelancer.getName());
        freelancerRecord.setRate(freelancer.getRate());
        freelancerRecord.setLocation(freelancer.getLocation());
        freelancerRepository.save(freelancerRecord);

        return freelancer;
    }
    
    public Freelancer findById(String id) {
        return freelancerRepository
                .findById(id)
                .map(freelancer -> new Freelancer(freelancer.getId(),
                        freelancer.getName(),
                        freelancer.getExpertise(),
                        freelancer.getRate(),
                        freelancer.getLocation(),
                        freelancer.getContact()))
                .orElse(null);
    }

    public Freelancer updateFreelancer(String id) {
        return null;
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
