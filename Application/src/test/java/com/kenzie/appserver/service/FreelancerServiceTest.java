package com.kenzie.appserver.service;

import com.kenzie.appserver.repositories.FreelancerRepository;
import com.kenzie.appserver.repositories.model.FreelancerRecord;
import com.kenzie.appserver.service.model.Freelancer;
import com.kenzie.capstone.service.client.LambdaServiceClient;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
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

    @Test
    void findAll() {
        FreelancerRecord record1 = new FreelancerRecord();
        record1.setContact("contact1");
        record1.setCreatedAt(ZonedDateTime.now());
        record1.setId(randomUUID().toString());
        record1.setExpertise(new ArrayList<>(List.of("fix", "idk")));
        record1.setLocation("NYC");
        record1.setName("Bob");
        record1.setModifiedAt(ZonedDateTime.now());

        FreelancerRecord record2 = new FreelancerRecord();
        record2.setContact("contact2");
        record2.setCreatedAt(ZonedDateTime.now());
        record2.setId(randomUUID().toString());
        record2.setExpertise(new ArrayList<>(List.of("break", "idk")));
        record2.setLocation("Queens");
        record2.setName("Tom");
        record2.setModifiedAt(ZonedDateTime.now());

        List<FreelancerRecord> recordList = new ArrayList<>();
        recordList.add(record1);
        recordList.add(record2);
        when(freelancerRepository.findAll()).thenReturn(recordList);

        List<Freelancer> freelancers = freelancerService.findAll();

        Assertions.assertNotNull(freelancers, "The freelancer list is returned");
        Assertions.assertEquals(2, freelancers.size(), "Proper amount of freelancers");

        for (Freelancer freelancer : freelancers) {
            if (freelancer.getId().equals(record1.getId())) {
                Assertions.assertEquals(record1.getId(), freelancer.getId(), "The freelancer id matches");
                Assertions.assertEquals(record1.getContact(), freelancer.getContact(), "The freelancer id matches");
                Assertions.assertEquals(record1.getExpertise(), freelancer.getExpertise(), "The freelancer id matches");
                Assertions.assertEquals(record1.getLocation(), freelancer.getLocation(), "The freelancer id matches");
                Assertions.assertEquals(record1.getName(), freelancer.getName(), "The freelancer id matches");
                Assertions.assertNotNull(record1.getCreatedAt(), "There is a creation time");
                Assertions.assertNotNull(record1.getModifiedAt(), "There is a modified time");
            } else if (freelancer.getId().equals(record2.getId())) {
                Assertions.assertEquals(record2.getId(), freelancer.getId(), "The freelancer id matches");
                Assertions.assertEquals(record2.getContact(), freelancer.getContact(), "The freelancer id matches");
                Assertions.assertEquals(record2.getExpertise(), freelancer.getExpertise(), "The freelancer id matches");
                Assertions.assertEquals(record2.getLocation(), freelancer.getLocation(), "The freelancer id matches");
                Assertions.assertEquals(record2.getName(), freelancer.getName(), "The freelancer id matches");
                Assertions.assertNotNull(record2.getCreatedAt(), "There is a creation time");
                Assertions.assertNotNull(record2.getModifiedAt(), "There is a modified time");
            } else {
                Assertions.assertTrue(false, "Freelancer returned that was not in the records");
            }
        }
    }


}
