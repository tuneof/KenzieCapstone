package com.kenzie.capstone.service.model;

public class HireResponse {
    private String freelancerId;
    private String hireId;
    private String hireDate;

    public HireResponse(String freelancerId, String hireId, String hireDate) {
        this.freelancerId = freelancerId;
        this.hireId = hireId;
        this.hireDate = hireDate;
    }

    public HireResponse() {}

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

    public String getHireDate() {
        return hireDate;
    }

    public void setHireDate(String hireDate) {
        this.hireDate = hireDate;
    }

    @Override
    public String toString() {
        return "HireResponse{" +
                "freelancerId='" + freelancerId + '\'' +
                ", hireId='" + hireId + '\'' +
                ", hireDate='" + hireDate + '\'' +
                '}';
    }
}
