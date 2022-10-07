package com.kenzie.capstone.service.lambda;

import com.kenzie.capstone.service.HireStatusService;
import com.kenzie.capstone.service.converter.JsonStringToHireStatusConverter;
import com.kenzie.capstone.service.dependency.ServiceComponent;
import com.kenzie.capstone.service.dependency.DaggerServiceComponent;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.kenzie.capstone.service.model.HireRequest;
import com.kenzie.capstone.service.model.HireResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class SetHireStatus implements RequestHandler<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> {

    static final Logger log = LogManager.getLogger();

    @Override
    public APIGatewayProxyResponseEvent handleRequest(APIGatewayProxyRequestEvent input, Context context) {
        JsonStringToHireStatusConverter jsonStringToHireStatusConverter = new JsonStringToHireStatusConverter();
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();

        log.info(gson.toJson(input));

        ServiceComponent serviceComponent = DaggerServiceComponent.create();
        HireStatusService hireService = serviceComponent.provideHireStatusService();

//        Map<String, String> headers = new HashMap<>();
//        headers.put("Content-Type", "application/json");

        APIGatewayProxyResponseEvent response = new APIGatewayProxyResponseEvent();
                //.withHeaders(headers);

//        String status = input.getBody();
//
//        if (status == null || status.length() == 0) {
//            return response
//                    .withStatusCode(400)
//                    .withBody("data is invalid");
//        }

        try {
            HireRequest hireRequest = jsonStringToHireStatusConverter.convert(input.getBody());
            HireResponse hireResponse = hireService.setHireStatus(hireRequest);
            return response
                    .withStatusCode(200)
                    .withBody(gson.toJson(hireResponse));

        } catch (Exception e) {
            return response
                    .withStatusCode(400)
                    .withBody(gson.toJson(e.getMessage()));
        }
    }
}
