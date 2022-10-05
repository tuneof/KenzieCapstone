package com.kenzie.capstone.service.converter;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.kenzie.capstone.service.exceptions.InvalidDataException;
import com.kenzie.capstone.service.model.HireRequest;

public class JsonStringToHireStatusConverter {

    public HireRequest convert(String body) {
        try {
            GsonBuilder builder = new GsonBuilder();
            Gson gson = builder.create();
            HireRequest hireRequest = gson.fromJson(body, HireRequest.class);
            return hireRequest;
        } catch (Exception e) {
            throw new InvalidDataException("Referral could not be deserialized");
        }
    }
}
