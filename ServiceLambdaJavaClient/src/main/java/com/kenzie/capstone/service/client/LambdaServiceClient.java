package com.kenzie.capstone.service.client;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kenzie.capstone.service.model.ExampleData;
import com.kenzie.capstone.service.model.HireRequest;
import com.kenzie.capstone.service.model.HireResponse;
import com.kenzie.capstone.service.model.TopHires;

import java.util.List;


public class LambdaServiceClient {

    private static final String ADD_REFERRAL_ENDPOINT = "hire/add";
    private static final String GET_REFERRAL_SUMMARY_ENDPOINT = "hire/{freelancerId}";
    private static final String GET_TOPHIRES_ENDPOINT = "hire/tophires";

    private ObjectMapper mapper;

    public LambdaServiceClient() {
        this.mapper = new ObjectMapper();
    }

//    public ExampleData getExampleData(String id) {
//        EndpointUtility endpointUtility = new EndpointUtility();
//        String response = endpointUtility.getEndpoint(GET_EXAMPLE_ENDPOINT.replace("{id}", id));
//        ExampleData exampleData;
//        try {
//            exampleData = mapper.readValue(response, ExampleData.class);
//        } catch (Exception e) {
//            throw new ApiGatewayException("Unable to map deserialize JSON: " + e);
//        }
//        return exampleData;
//    }
//
//    public ExampleData setExampleData(String data) {
//        EndpointUtility endpointUtility = new EndpointUtility();
//        String response = endpointUtility.postEndpoint(SET_EXAMPLE_ENDPOINT, data);
//        ExampleData exampleData;
//        try {
//            exampleData = mapper.readValue(response, ExampleData.class);
//        } catch (Exception e) {
//            throw new ApiGatewayException("Unable to map deserialize JSON: " + e);
//        }
//        return exampleData;
//    }

    public HireResponse addHire(HireRequest hireRequest) {
        EndpointUtility endpointUtility = new EndpointUtility();
        String request;
        try {
            request = mapper.writeValueAsString(hireRequest);
        } catch(JsonProcessingException e) {
            throw new ApiGatewayException("Unable to serialize request: " + e);
        }
        String response = endpointUtility.postEndpoint(ADD_REFERRAL_ENDPOINT, request);
        HireResponse hireResponse;
        try {
            hireResponse = mapper.readValue(response, HireResponse.class);
        } catch (Exception e) {
            throw new ApiGatewayException("Unable to map deserialize JSON: " + e);
        }
        return hireResponse;
    }

    public List<TopHires> getTopHires() {
        EndpointUtility endpointUtility = new EndpointUtility();
        String response = endpointUtility.getEndpoint(GET_TOPHIRES_ENDPOINT);
        List<TopHires> topHires;
        try {
            topHires = mapper.readValue(response, new TypeReference<>(){});
        } catch (Exception e) {
            throw new ApiGatewayException("Unable to map deserialize JSON: " + e);
        }
        return topHires;
    }
}
