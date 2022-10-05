package com.kenzie.appserver.service;

import com.kenzie.appserver.repositories.FreelancerRepository;
import com.kenzie.appserver.repositories.model.FreelancerRecord;
import com.kenzie.appserver.service.model.Freelancer;
import com.kenzie.capstone.service.client.HireStatusServiceClient;
import com.kenzie.capstone.service.model.HireRequest;
import com.kenzie.capstone.service.model.HireResponse;
import com.kenzie.capstone.service.model.HireStatus;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class FreelancerService {
    private FreelancerRepository freelancerRepository;
    private HireStatusServiceClient hireStatusServiceClient;

    public FreelancerService(FreelancerRepository repository, HireStatusServiceClient client){
        this.freelancerRepository = repository;
        this.hireStatusServiceClient = client;
    }

    public List<Freelancer> findAll() {
        List<Freelancer> freelancers = new ArrayList<>();
        freelancerRepository
                .findAll()
                .forEach(freelancer -> freelancers.add(toFreelancer(freelancer)));

        return freelancers;
    }

    public Freelancer addNewFreelancer(Freelancer freelancer) {
        if (freelancer == null) {
            throw new IllegalArgumentException();
        }

        FreelancerRecord freelancerRecord = new FreelancerRecord();
        freelancerRecord.setId(freelancer.getId());
        freelancerRecord.setCreatedAt(ZonedDateTime.now());
        freelancerRecord.setContact(freelancer.getContact());
        freelancerRecord.setExpertise(freelancer.getExpertise());
        freelancerRecord.setName(freelancer.getName());
        freelancerRecord.setRate(freelancer.getRate());
        freelancerRecord.setLocation(freelancer.getLocation());
        freelancerRepository.save(freelancerRecord);

        HireRequest hireRequest = new HireRequest();
        hireRequest.setFreelancerId(freelancerRecord.getId());
        hireRequest.setStatus("Not hired");
        hireStatusServiceClient.setHireStatus(hireRequest);

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

    public String getFreelancerHireStatus(String freelancerId) {
        HireStatus hireStatus = hireStatusServiceClient.getHireStatus(freelancerId);

        return hireStatus.getStatus();
    }

    public HireStatus updateFreelancerHireStatus(String freelancerId, String status) {
        HireStatus hireStatus = new HireStatus(freelancerId, status);
        hireStatusServiceClient.updateHireStatus(hireStatus);

        return hireStatus;
    }

    public void updateFreelancer(Freelancer freelancer) {
        //if the freelancer that is being updated doesn't exist, returns null value
        Optional<FreelancerRecord> optionalFreelancerRecord = freelancerRepository.findById(freelancer.getId());

        if (!optionalFreelancerRecord.isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Freelancer does not exist.");
        }

        FreelancerRecord record = optionalFreelancerRecord.get();

        if (!record.getName().equals(freelancer.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Only the person who created this profile may update it.");
        }
        //return freelancer;
        freelancerRepository.save(toRecord(freelancer));
    }

    private FreelancerRecord toRecord(Freelancer freelancer) {
        FreelancerRecord freelancerRecord = new FreelancerRecord();
        freelancerRecord.setId(freelancer.getId());
        freelancerRecord.setModifiedAt(ZonedDateTime.now());
        freelancerRecord.setContact(freelancer.getContact());
        freelancerRecord.setExpertise(freelancer.getExpertise());
        freelancerRecord.setName(freelancer.getName());
        freelancerRecord.setRate(freelancer.getRate());
        freelancerRecord.setLocation(freelancer.getLocation());
        return freelancerRecord;
    }

    private Freelancer toFreelancer(FreelancerRecord record) {
        return new Freelancer(record.getId(),
                record.getName(),
                record.getExpertise(),
                record.getRate(),
                record.getLocation(),
                record.getContact());
    }

    public void deleteFreelancer(String id){
        freelancerRepository.deleteById(id);
    }
}
