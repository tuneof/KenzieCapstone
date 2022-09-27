package com.kenzie.capstone.service.model;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;

import java.util.Objects;

@DynamoDBTable(tableName = "HireStatus")
public class HireStatusRecord {
    private String freelancerId;
    private String hireStatusId;
    private String status;

    @DynamoDBHashKey(attributeName = "FreelancerId")
    public String getFreelancerId() {
        return freelancerId;
    }

    public void setFreelancerId(String freelancerId) {
        this.freelancerId = freelancerId;
    }

    @DynamoDBAttribute(attributeName = "HireStatusId")
    public String getHireStatusId() {
        return hireStatusId;
    }

    public void setHireStatusId(String hireStatusId) {
        this.hireStatusId = hireStatusId;
    }

    @DynamoDBAttribute(attributeName = "Status")
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
        HireStatusRecord that = (HireStatusRecord) o;
        return Objects.equals(freelancerId, that.freelancerId) && Objects.equals(hireStatusId, that.hireStatusId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(freelancerId, hireStatusId);
    }
}
