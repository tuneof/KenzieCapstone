package com.kenzie.capstone.service.client;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kenzie.capstone.service.model.FreelancerHires;
import com.kenzie.capstone.service.model.HireRequest;
import com.kenzie.capstone.service.model.HireResponse;
import com.kenzie.capstone.service.model.TopHiresEntry;

import java.util.List;


public class HireServiceClient {

    private static final String ADD_HIRE_ENDPOINT = "hire/add";
    private static final String GET_HIRE_SUMMARY_ENDPOINT = "hire/{freelancerId}";
    private static final String GET_TOPHIRES_ENDPOINT = "hire/tophires";

    private ObjectMapper mapper;

    public HireServiceClient() {
        this.mapper = new ObjectMapper();
    }

    public HireResponse addHire(HireRequest hireRequest) {
        EndpointUtility endpointUtility = new EndpointUtility();
        String request;
        try {
            request = mapper.writeValueAsString(hireRequest);
        } catch(JsonProcessingException e) {
            throw new ApiGatewayException("Unable to serialize request: " + e);
        }
        String response = endpointUtility.postEndpoint(ADD_HIRE_ENDPOINT, request);
        HireResponse hireResponse;
        try {
            hireResponse = mapper.readValue(response, HireResponse.class);
        } catch (Exception e) {
            throw new ApiGatewayException("Unable to map deserialize JSON: " + e);
        }
        return hireResponse;
    }

    public FreelancerHires getHires(String freelancerId) {
        EndpointUtility endpointUtility = new EndpointUtility();
        String response = endpointUtility.getEndpoint(GET_HIRE_SUMMARY_ENDPOINT.replace("{freelancerId}", freelancerId));
        FreelancerHires hires;
        try {
            hires = mapper.readValue(response, FreelancerHires.class);
        } catch (Exception e) {
            throw new ApiGatewayException("Unable to map deserialize JSON: " + e);
        }
        return hires;
    }

    public List<TopHiresEntry> getTopHires() {
        EndpointUtility endpointUtility = new EndpointUtility();
        String response = endpointUtility.getEndpoint(GET_TOPHIRES_ENDPOINT);
        List<TopHiresEntry> topHireEntries;
        try {
            topHireEntries = mapper.readValue(response, new TypeReference<>(){});
        } catch (Exception e) {
            throw new ApiGatewayException("Unable to map deserialize JSON: " + e);
        }
        return topHireEntries;
    }

}
