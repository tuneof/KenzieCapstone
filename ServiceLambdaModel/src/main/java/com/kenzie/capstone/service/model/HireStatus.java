package com.kenzie.capstone.service.model;

import java.util.Objects;
import java.util.UUID;

public class HireStatus {
    private String freelancerId;
    private String hireStatusId;
    private String status;

    public HireStatus(String freelancerId, String status) {
        this.freelancerId = freelancerId;
        this.hireStatusId = UUID.randomUUID().toString();
        this.status = status;
    }

    public String getFreelancerId() {
        return freelancerId;
    }

    public void setFreelancerId(String freelancerId) {
        this.freelancerId = freelancerId;
    }

    public String getHireStatusId() {
        return hireStatusId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HireStatus that = (HireStatus) o;
        return Objects.equals(freelancerId, that.freelancerId) && Objects.equals(hireStatusId, that.hireStatusId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(freelancerId, hireStatusId);
    }
}
