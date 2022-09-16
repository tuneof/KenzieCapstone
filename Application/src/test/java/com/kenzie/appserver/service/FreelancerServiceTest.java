package com.kenzie.appserver.service;

import com.kenzie.appserver.repositories.FreelancerRepository;
import com.kenzie.appserver.repositories.model.FreelancerRecord;
import com.kenzie.appserver.service.model.Freelancer;
import com.kenzie.capstone.service.client.LambdaServiceClient;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static java.util.UUID.randomUUID;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class FreelancerServiceTest {
    private FreelancerRepository freelancerRepository;
    private FreelancerService freelancerService;
    private LambdaServiceClient lambdaServiceClient;

    @BeforeEach
    void setup() {
        freelancerRepository = mock(FreelancerRepository.class);
        lambdaServiceClient = mock(LambdaServiceClient.class);
        freelancerService = new FreelancerService(freelancerRepository, lambdaServiceClient);
    }
    /** ------------------------------------------------------------------------
     *  exampleService.findById
     *  ------------------------------------------------------------------------ **/

    @Test
    void findById() {
        // GIVEN
        String id = randomUUID().toString();

        FreelancerRecord record = new FreelancerRecord();
        record.setId(id);
        record.setName("freelancerName");

        // WHEN
        when(freelancerRepository.findById(id)).thenReturn(Optional.of(record));
        Freelancer freelancer = freelancerService.findById(id);

        // THEN
        Assertions.assertNotNull(freelancer, "The object is returned");
        Assertions.assertEquals(record.getId(), freelancer.getId(), "The id matches");
        Assertions.assertEquals(record.getName(), freelancer.getName(), "The name matches");
    }

    @Test
    void findById_invalid() {
        // GIVEN
        String id = randomUUID().toString();

        when(freelancerRepository.findById(id)).thenReturn(Optional.empty());

        // WHEN
        Freelancer freelancer = freelancerService.findById(id);

        // THEN
        Assertions.assertNull(freelancer, "The example is null when not found");
    }

}
