package com.kenzie.appserver.service.model;

import java.util.List;

public class Freelancer {
    private String id;
    private String name;
    private List<String> expertise;
    private String rate;
    private String location;
    private String contact;

    public Freelancer(String id, String name, List<String> expertise, String rate, String location, String contact){
        this.id = id;
        this.name = name;
        this.expertise = expertise;
        this.rate = rate;
        this.location = location;
        this.contact = contact;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<String> getExpertise() {
        return expertise;
    }

    public String getRate() {
        return rate;
    }

    public String getLocation() {
        return location;
    }

    public String getContact() {
        return contact;
    }
}
