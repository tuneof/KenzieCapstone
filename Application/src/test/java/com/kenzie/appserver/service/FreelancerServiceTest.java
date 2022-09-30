package com.kenzie.appserver.service;

import com.kenzie.appserver.repositories.FreelancerRepository;
import com.kenzie.appserver.repositories.model.FreelancerRecord;
import com.kenzie.appserver.service.model.Freelancer;
import com.kenzie.capstone.service.client.HireStatusServiceClient;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static java.util.UUID.randomUUID;
import static org.mockito.Mockito.*;

public class FreelancerServiceTest {
    private FreelancerRepository freelancerRepository;
    private FreelancerService freelancerService;
    private HireStatusServiceClient hireServiceClient;

    @BeforeEach
    void setup() {
        freelancerRepository = mock(FreelancerRepository.class);
        hireServiceClient = mock(HireStatusServiceClient.class);
        freelancerService = new FreelancerService(freelancerRepository, hireServiceClient);
    }
    /** ------------------------------------------------------------------------
     *  FreelancerService.findById
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

    @Test
    void addNewFreelancer() {
        String id = randomUUID().toString();
        String contact = "911";
        List<String> expertise = new ArrayList<>(List.of("break", "idk"));
        String name = "Fred";
        String rate = "$10";
        String location = "New York";

        Freelancer freelancer = new Freelancer(id, name, expertise, rate, location, contact);
        ArgumentCaptor<FreelancerRecord> freelancerRecordCaptor = ArgumentCaptor.forClass(FreelancerRecord.class);

        Freelancer returnedFreelancer = freelancerService.addNewFreelancer(freelancer);

        Assertions.assertNotNull(returnedFreelancer);

        verify(freelancerRepository).save(freelancerRecordCaptor.capture());

        FreelancerRecord record = freelancerRecordCaptor.getValue();

        Assertions.assertNotNull(record, "The freelancer record is returned and saved");
        Assertions.assertEquals(record.getId(), id, "The freelancer id matches");
        Assertions.assertEquals(record.getName(), name, "The freelancer name matches");
        Assertions.assertEquals(record.getExpertise(), expertise, "The freelancer's expertise matches");
        Assertions.assertEquals(record.getRate(), rate, "The freelancer rate matches");
        Assertions.assertEquals(record.getLocation(), location, "The freelancer location matches");
        Assertions.assertEquals(record.getContact(), contact, "The freelancer contact matches");
    }

    @Test
    void addNewFreelancer_freelancer_null() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> freelancerService.addNewFreelancer(null));
    }

    /** ------------------------------------------------------------------------
     *  freelancerService.updateFreelancer
     *  ------------------------------------------------------------------------ **/

    @Test
    void updateFreelancer_success() {
        //GIVEN
        String id = randomUUID().toString();
        String contact = "911";
        List<String> expertise = new ArrayList<>(List.of("break", "idk"));
        String name = "Fred";
        String rate = "$10";
        String location = "New York";

        FreelancerRecord record = new FreelancerRecord();
        record.setId(id);
        record.setName(name);
        record.setExpertise(expertise);
        record.setRate(rate);
        record.setLocation(location);
        record.setContact(contact);

        Freelancer expectedFreelancer = new Freelancer(id, name, expertise, rate, location, contact);

        when(freelancerRepository.findById(id)).thenReturn(Optional.of(record));

        //WHEN
        freelancerService.updateFreelancer(expectedFreelancer);

        //THEN
        Freelancer actualFreelancer = freelancerService.findById(id);
        verify(freelancerRepository, times(1)).save(any());
        Assertions.assertEquals(actualFreelancer.getId(), expectedFreelancer.getId());
        Assertions.assertEquals(actualFreelancer.getName(), expectedFreelancer.getName());
        Assertions.assertEquals(actualFreelancer.getExpertise(), expectedFreelancer.getExpertise());
        Assertions.assertEquals(actualFreelancer.getRate(), expectedFreelancer.getRate());
        Assertions.assertEquals(actualFreelancer.getLocation(), expectedFreelancer.getLocation());
        Assertions.assertEquals(actualFreelancer.getContact(), expectedFreelancer.getContact());
    }

    @Test
    void updateFreelancer_doesNotExist_fail() {
        //GIVEN
        String id = randomUUID().toString();
        String contact = "911";
        List<String> expertise = new ArrayList<>(List.of("break", "idk"));
        String name = "Fred";
        String rate = "$10";
        String location = "New York";

        Freelancer expectedFreelancer = new Freelancer(id, name, expertise, rate, location, contact);

        when(freelancerRepository.findById(id)).thenReturn(Optional.empty());

        //WHEN
        //THEN
        Assertions.assertThrows(IllegalArgumentException.class, () -> freelancerService.updateFreelancer(expectedFreelancer));
    }

    @Test
    void deleteFreelancer_freelancerExistsToDelete(){
        String id = randomUUID().toString();
        String contact = "911";
        List<String> expertise = new ArrayList<>(List.of("break", "idk"));
        String name = "Fred";
        String rate = "$10";
        String location = "New York";

        Freelancer expectedFreelancer = new Freelancer(id, name, expertise, rate, location, contact);

        freelancerService.deleteFreelancer(expectedFreelancer.getId());

        verify(freelancerRepository).deleteById(any());
    }

    @Test
    void deleteFreelancer_freelancerDoesNotExist(){
        String id = randomUUID().toString();

        freelancerService.deleteFreelancer(id);

        verify(freelancerRepository).deleteById(id);
    }
}
