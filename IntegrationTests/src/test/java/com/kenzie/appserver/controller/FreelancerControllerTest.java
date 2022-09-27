package com.kenzie.appserver.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.kenzie.appserver.IntegrationTest;
import com.kenzie.appserver.controller.model.FreelancerCreateRequest;
import com.kenzie.appserver.controller.model.FreelancerResponse;
import com.kenzie.appserver.controller.model.FreelancerUpdateRequest;
import com.kenzie.appserver.service.FreelancerService;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.kenzie.appserver.service.model.Freelancer;
import net.andreinc.mockneat.MockNeat;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static java.util.UUID.randomUUID;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@IntegrationTest
class FreelancerControllerTest {
    @Autowired
    private MockMvc mvc;

    @Autowired
    FreelancerService freelancerService;

    private final MockNeat mockNeat = MockNeat.threadLocal();

    private final ObjectMapper mapper = new ObjectMapper();

    @Test
    public void getById_Exists() throws Exception {
        FreelancerCreateRequest createRequest = new FreelancerCreateRequest();
        createRequest.setName("Behzod");
        createRequest.setContact("bmamadiev@gmail.com");
        createRequest.setRate("45/hour");
        createRequest.setLocation("Washington DC");
        createRequest.setExpertise(new ArrayList<>(Arrays.asList("Java", "JavaScript", "HTML", "DynamoDB", "HTML")));

        mapper.registerModule(new JavaTimeModule());

        String createResponse = mvc.perform(post("/freelancers")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(createRequest)))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        FreelancerResponse createFreelancerResponse = mapper.readValue(createResponse, new TypeReference<FreelancerResponse>() {} );
        String id = createFreelancerResponse.getId();

        mvc.perform(get("/freelancers/{id}", id)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(createRequest)))
                .andExpect(status().isOk());
        //clean up
//        mvc.perform(delete("/freelancers/delete/{id}", id))
//                .andExpect(status().isNoContent());
    }

    @Test
    public void createFreelancer_CreateSuccessful() throws Exception {
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
    public void getAllFreelancers_success() throws Exception {
        String id = randomUUID().toString();
        String contact = "911";
        List<String> expertise = new ArrayList<>(List.of("break", "idk"));
        String name = "Fred";
        String rate = "$10";
        String location = "New York";

        String id1 = randomUUID().toString();
        String contact1 = "119";
        List<String> expertise1 = new ArrayList<>(List.of("no idea", "what"));
        String name1 = "Bob";
        String rate1 = "$15";
        String location1 = "California";

        Freelancer freelancer = new Freelancer(id, name, expertise, rate, location, contact);
        Freelancer freelancer1 = new Freelancer(id1, name1, expertise1, rate1, location1, contact1);

        freelancerService.addNewFreelancer(freelancer);
        freelancerService.addNewFreelancer(freelancer1);

        ResultActions actions = mvc.perform(get("/freelancers")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful());

        String responseBody = actions.andReturn().getResponse().getContentAsString();
        List<Freelancer> responses = mapper.readValue(responseBody, new TypeReference<List<Freelancer>>() {});
        Assertions.assertThat(responses.size()).isGreaterThan(0).as("There are responses");
        for (Freelancer response : responses) {
            Assertions.assertThat(response.getId()).isNotEmpty().as("The ID is populated");
            Assertions.assertThat(response.getName()).isNotEmpty().as("The name is populated");
            Assertions.assertThat(response.getLocation()).isNotEmpty().as("The location is populated");
            Assertions.assertThat(response.getContact()).isNotEmpty().as("The contact is populated");
            Assertions.assertThat(response.getExpertise()).isNotEmpty().as("The expertise is populated");
            Assertions.assertThat(response.getRate()).isNotEmpty().as("The rate is populated");
        }
    }
}