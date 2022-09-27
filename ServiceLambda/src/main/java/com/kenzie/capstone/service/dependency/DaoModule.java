package com.kenzie.capstone.service.dependency;


import com.kenzie.capstone.service.dao.HireStatusDao;
import com.kenzie.capstone.service.util.DynamoDbClientProvider;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import dagger.Module;
import dagger.Provides;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

/**
 * Provides DynamoDBMapper instance to DAO classes.
 */
@Module
public class DaoModule {

    @Singleton
    @Provides
    @Named("DynamoDBMapper")
    public DynamoDBMapper provideDynamoDBMapper() {
        return new DynamoDBMapper(DynamoDbClientProvider.getDynamoDBClient());
    }

    @Singleton
    @Provides
    @Named("HireStatusDao")
    @Inject
    public HireStatusDao provideExampleDao(@Named("DynamoDBMapper") DynamoDBMapper mapper) {
        return new HireStatusDao(mapper);
    }

}
