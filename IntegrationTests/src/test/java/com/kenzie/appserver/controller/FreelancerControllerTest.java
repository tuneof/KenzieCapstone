package com.kenzie.appserver.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.kenzie.appserver.IntegrationTest;
import com.kenzie.appserver.controller.model.FreelancerCreateRequest;
import com.kenzie.appserver.controller.model.FreelancerUpdateRequest;
import com.kenzie.appserver.service.ExampleService;
import com.kenzie.appserver.service.FreelancerService;
import com.kenzie.appserver.service.model.Example;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import net.andreinc.mockneat.MockNeat;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static java.util.UUID.randomUUID;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@IntegrationTest
class FreelancerControllerTest {
    @Autowired
    private MockMvc mvc;

    @Autowired
    ExampleService exampleService;
    FreelancerService freelancerService;

    private final MockNeat mockNeat = MockNeat.threadLocal();

    private final ObjectMapper mapper = new ObjectMapper();

    @Test
    public void getById_Exists() throws Exception {

        String name = mockNeat.strings().valStr();

        Example persistedExample = exampleService.addNewExample(name);
        mvc.perform(get("/example/{id}", persistedExample.getId())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("id")
                        .isString())
                .andExpect(jsonPath("name")
                        .value(is(name)))
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    public void createExample_CreateSuccessful() throws Exception {
        String name = mockNeat.strings().valStr();

        FreelancerCreateRequest freelancerCreateRequest = new FreelancerCreateRequest();
        freelancerCreateRequest.setName(name);

        mapper.registerModule(new JavaTimeModule());

        mvc.perform(post("/example")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(freelancerCreateRequest)))
                .andExpect(jsonPath("id")
                        .exists())
                .andExpect(jsonPath("name")
                        .value(is(name)))
                .andExpect(status().is2xxSuccessful());
    }
    @Test
    public void updateFreelancer_updateSuccess() throws Exception {
        String id = randomUUID().toString();
        String contact = mockNeat.strings().valStr();
        List<String> expertise = new ArrayList<>(List.of(mockNeat.strings().valStr(), mockNeat.strings().valStr()));
        String name = mockNeat.strings().valStr();
        String rate = mockNeat.strings().valStr();
        String location = mockNeat.strings().valStr();

        FreelancerUpdateRequest updateRequest = new FreelancerUpdateRequest();
        updateRequest.setId(id);
        updateRequest.setName(name);
        updateRequest.setExpertise(expertise);
        updateRequest.setRate(rate);
        updateRequest.setLocation(location);
        updateRequest.setContact(contact);

        mapper.registerModule(new JavaTimeModule());

        mvc.perform(post("/freelancers")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(updateRequest)))
                .andExpect(jsonPath("id")
                        .exists())
                .andExpect(jsonPath("name")
                        .value(is(name)))
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    public void deleteFreelancer_deleteSuccess() throws Exception {
        String id = randomUUID().toString();
        String contact = mockNeat.strings().valStr();
        List<String> expertise = new ArrayList<>(List.of(mockNeat.strings().valStr(), mockNeat.strings().valStr()));
        String name = mockNeat.strings().valStr();
        String rate = mockNeat.strings().valStr();
        String location = mockNeat.strings().valStr();

        FreelancerUpdateRequest updateRequest = new FreelancerUpdateRequest();
        updateRequest.setId(id);
        updateRequest.setName(name);
        updateRequest.setExpertise(expertise);
        updateRequest.setRate(rate);
        updateRequest.setLocation(location);
        updateRequest.setContact(contact);

        mvc.perform(delete("/delete/{id}"))
                .andExpect(jsonPath("id").exists())
                .andExpect(status().is2xxSuccessful());
    }
}