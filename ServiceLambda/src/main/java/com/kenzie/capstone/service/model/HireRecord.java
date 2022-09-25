package com.kenzie.capstone.service.model;

import com.amazonaws.services.dynamodbv2.datamodeling.*;
import com.kenzie.capstone.service.converter.ZonedDateTimeConverter;

import java.time.ZonedDateTime;
import java.util.Objects;

@DynamoDBTable(tableName = "Hire")
public class HireRecord {

    private String freelancerId;
    private String hireId;
    private ZonedDateTime dateHired;

    @DynamoDBHashKey(attributeName = "FreelancerId")
    public String getFreelancerId() {
        return freelancerId;
    }

    @DynamoDBAttribute(attributeName = "HireId")
    @DynamoDBIndexHashKey(globalSecondaryIndexName = "ReferrerIdIndex", attributeName = "ReferrerId")
    public String getHireId() {
        return hireId;
    }

    @DynamoDBAttribute(attributeName = "DateHired")
    @DynamoDBTypeConverted(converter = ZonedDateTimeConverter.class)
    public ZonedDateTime getDateHired() {
        return dateHired;
    }

    public void setFreelancerId(String freelancerId) {
        this.freelancerId = freelancerId;
    }

    public void setHireId(String hireId) {
        this.hireId = hireId;
    }

    public void setDateHired(ZonedDateTime dateHired) {
        this.dateHired = dateHired;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HireRecord that = (HireRecord) o;
        return Objects.equals(freelancerId, that.freelancerId) && Objects.equals(hireId, that.hireId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(freelancerId, hireId);
    }
}