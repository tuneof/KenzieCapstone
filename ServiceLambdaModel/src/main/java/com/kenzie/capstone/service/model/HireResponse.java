package com.kenzie.capstone.service.model;

public class HireResponse {
    private String freelancerId;
    private String status;

    public HireResponse(String freelancerId, String status) {
        this.freelancerId = freelancerId;
        this.status = status;
    }

    public HireResponse() {

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
        return "HireResponse{" +
                "freelancerId='" + freelancerId + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
