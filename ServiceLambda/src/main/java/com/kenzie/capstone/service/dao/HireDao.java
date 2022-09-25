package com.kenzie.capstone.service.dao;

import com.kenzie.capstone.service.exceptions.InvalidDataException;
import com.kenzie.capstone.service.model.ExampleData;
import com.kenzie.capstone.service.model.HireRecord;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBSaveExpression;
import com.amazonaws.services.dynamodbv2.model.ConditionalCheckFailedException;
import com.amazonaws.services.dynamodbv2.model.ExpectedAttributeValue;
import com.google.common.collect.ImmutableMap;

import java.util.List;

public class HireDao {
    private DynamoDBMapper mapper;

    /**
     * Allows access to and manipulation of Match objects from the data store.
     * @param mapper Access to DynamoDB
     */
    public HireDao(DynamoDBMapper mapper) {
        this.mapper = mapper;
    }

//    public ExampleData storeExampleData(ExampleData exampleData) {
//        try {
//            mapper.save(exampleData, new DynamoDBSaveExpression()
//                    .withExpected(ImmutableMap.of(
//                            "id",
//                            new ExpectedAttributeValue().withExists(false)
//                    )));
//        } catch (ConditionalCheckFailedException e) {
//            throw new IllegalArgumentException("id has already been used");
//        }
//
//        return exampleData;
//    }
//
//    public List<HireRecord> getExampleData(String id) {
//        HireRecord hireRecord = new HireRecord();
//        hireRecord.setId(id);
//
//        DynamoDBQueryExpression<HireRecord> queryExpression = new DynamoDBQueryExpression<HireRecord>()
//                .withHashKeyValues(hireRecord)
//                .withConsistentRead(false);
//
//        return mapper.query(HireRecord.class, queryExpression);
//    }
//
//    public HireRecord setExampleData(String id, String data) {
//        HireRecord hireRecord = new HireRecord();
//        hireRecord.setId(id);
//        hireRecord.setData(data);
//
//        try {
//            mapper.save(hireRecord, new DynamoDBSaveExpression()
//                    .withExpected(ImmutableMap.of(
//                            "id",
//                            new ExpectedAttributeValue().withExists(false)
//                    )));
//        } catch (ConditionalCheckFailedException e) {
//            throw new IllegalArgumentException("id already exists");
//        }
//
//        return hireRecord;
//    }
    public HireRecord addHire(HireRecord hire) {
        try {
            mapper.save(hire, new DynamoDBSaveExpression()
                    .withExpected(ImmutableMap.of(
                            "FreelancerId",
                            new ExpectedAttributeValue().withExists(false)
                    )));
        } catch (ConditionalCheckFailedException e) {
            throw new InvalidDataException("Freelancer has already been hired");
        }

        return hire;
    }

    public List<HireRecord> findByHireId(String hireId) {
        HireRecord hireRecord = new HireRecord();
        hireRecord.setHireId(hireId);

        DynamoDBQueryExpression<HireRecord> queryExpression = new DynamoDBQueryExpression<HireRecord>()
                .withHashKeyValues(hireRecord)
                .withIndexName("HireIdIndex")
                .withConsistentRead(false);

        return mapper.query(HireRecord.class, queryExpression);
    }
}
