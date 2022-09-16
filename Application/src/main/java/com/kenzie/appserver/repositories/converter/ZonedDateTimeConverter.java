package com.kenzie.appserver.repositories.converter;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTypeConverter;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class ZonedDateTimeConverter implements DynamoDBTypeConverter<String, ZonedDateTime> {
    @Override
    public String convert(ZonedDateTime dateTime) {
        return dateTime.format(DateTimeFormatter.ISO_INSTANT);
    }

    @Override
    public ZonedDateTime unconvert(String dateTimeRepresentation) {
        return ZonedDateTime.parse(dateTimeRepresentation);
    }
}
