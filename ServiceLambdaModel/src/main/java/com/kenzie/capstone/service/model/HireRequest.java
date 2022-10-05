package com.kenzie.capstone.service.model;

public class HireRequest {
    private String freelancerId;
    private String status;

    public HireRequest(String freelancerId, String status) {
        this.freelancerId = freelancerId;
        this.status = status;
    }

    public HireRequest() {

    }

    public String getFreelancerId() {
        return freelancerId;
    }

    public void setFreelancerId(String freelancerId) {
        this.freelancerId = freelancerId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "HireRequest{" +
                "freelancerId='" + freelancerId + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
