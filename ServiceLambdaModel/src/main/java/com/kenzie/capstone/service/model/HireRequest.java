package com.kenzie.capstone.service.model;

public class HireRequest {
    private String freelancerId;
    private String hireId;

    public HireRequest(String freelancerId, String hireId) {
        this.freelancerId = freelancerId;
        this.hireId = hireId;
    }

    public HireRequest() {}

    public String getFreelancerId() {
        return freelancerId;
    }

    public void setFreelancerId(String freelancerId) {
        this.freelancerId = freelancerId;
    }

    public String getHireId() {
        return hireId;
    }

    public void setHireId(String hireId) {
        this.hireId = hireId;
    }

    @Override
    public String toString() {
        return "HireRequest{" +
                "freelancerId='" + freelancerId + '\'' +
                ", hireId='" + hireId + '\'' +
                '}';
    }
}
