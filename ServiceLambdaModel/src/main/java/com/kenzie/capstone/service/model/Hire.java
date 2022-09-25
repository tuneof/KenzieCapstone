package com.kenzie.capstone.service.model;

import java.util.Objects;

public class Hire {
    private String freelancerId;
    private String hireId;
    private String hireDate;

    public Hire(String freelancerId, String hireId, String hireDate) {
        this.freelancerId = freelancerId;
        this.hireId = hireId;
        this.hireDate = hireDate;
    }

    public Hire() {}

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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Hire hire = (Hire) o;
        return Objects.equals(freelancerId, hire.freelancerId) && Objects.equals(hireId, hire.hireId) && Objects.equals(hireDate, hire.hireDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(freelancerId, hireId, hireDate);
    }

    @Override
    public String toString() {
        return "Hire{" +
                "freelancerId='" + freelancerId + '\'' +
                ", hireId='" + hireId + '\'' +
                ", hireDate='" + hireDate + '\'' +
                '}';
    }
}
