package com.kenzie.capstone.service.dao;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBSaveExpression;
import com.amazonaws.services.dynamodbv2.model.ConditionalCheckFailedException;
import com.amazonaws.services.dynamodbv2.model.ExpectedAttributeValue;
import com.google.common.collect.ImmutableMap;
import com.kenzie.capstone.service.model.HireStatus;
import com.kenzie.capstone.service.model.HireStatusRecord;

import java.util.List;

public class HireStatusDao {
    private DynamoDBMapper mapper;

    public HireStatusDao(DynamoDBMapper mapper) {
        this.mapper = mapper;
    }

    public HireStatus storeHireStatus(HireStatus hireStatus) {
        try {
            mapper.save(hireStatus, new DynamoDBSaveExpression()
                    .withExpected(ImmutableMap.of(
                            "FreelancerId",
                            new ExpectedAttributeValue().withExists(false)
                    )));
        } catch (ConditionalCheckFailedException e) {
            throw new IllegalArgumentException("FreelancerId has already been used");
        }

        return hireStatus;
    }

    public List<HireStatusRecord> getHireStatus(String freelancerId) {
        HireStatusRecord record = new HireStatusRecord();
        record.setFreelancerId(freelancerId);

        DynamoDBQueryExpression<HireStatusRecord> queryExpression = new DynamoDBQueryExpression<HireStatusRecord>()
                .withHashKeyValues(record)
                .withConsistentRead(false);

        return mapper.query(HireStatusRecord.class, queryExpression);
    }

    public HireStatusRecord setHireStatus(String freelancerId, String status) {
        HireStatusRecord record = new HireStatusRecord();
        record.setFreelancerId(freelancerId);
        record.setStatus(status);

        try {
            mapper.save(record, new DynamoDBSaveExpression()
                    .withExpected(ImmutableMap.of(
                            "FreelancerId",
                            new ExpectedAttributeValue().withExists(false)
                    )));
        } catch (ConditionalCheckFailedException e) {
            throw new IllegalArgumentException("FreelancerId already exists");
        }

        return record;
    }
}
