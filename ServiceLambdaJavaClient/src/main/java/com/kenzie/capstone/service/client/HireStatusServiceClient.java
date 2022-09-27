package com.kenzie.capstone.service.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kenzie.capstone.service.model.HireStatus;

public class HireStatusServiceClient {
    private static final String GET_HIRESTATUS_ENDPOINT = "hirestatus/{freelancerId}";
    private static final String SET_HIRESTATUS_ENDPOINT = "hirestatus";

    private ObjectMapper mapper;

    public HireStatusServiceClient() {
        this.mapper = new ObjectMapper();
    }

    public HireStatus getHireStatus(String freelancerId) {
        EndpointUtility endpointUtility = new EndpointUtility();
        String response = endpointUtility.getEndpoint(GET_HIRESTATUS_ENDPOINT.replace("{freelancerId}", freelancerId));
        HireStatus hireStatus;
        try {
            hireStatus = mapper.readValue(response, HireStatus.class);
        } catch (Exception e) {
            throw new ApiGatewayException("Unable to map deserialize JSON: " + e);
        }
        return hireStatus;
    }

    public HireStatus setHireStatus(String status) {
        EndpointUtility endpointUtility = new EndpointUtility();
        String response = endpointUtility.postEndpoint(SET_HIRESTATUS_ENDPOINT, status);
        HireStatus hireStatus;
        try {
            hireStatus = mapper.readValue(response, HireStatus.class);
        } catch (Exception e) {
            throw new ApiGatewayException("Unable to map deserialize JSON: " + e);
        }
        return hireStatus;
    }
}
