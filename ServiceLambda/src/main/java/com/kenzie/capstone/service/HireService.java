package com.kenzie.capstone.service;

import com.kenzie.capstone.service.converter.ZonedDateTimeConverter;
import com.kenzie.capstone.service.exceptions.InvalidDataException;
import com.kenzie.capstone.service.model.*;
import com.kenzie.capstone.service.dao.HireDao;

import javax.inject.Inject;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class HireService {

    private HireDao hireDao;
    private static ZonedDateTimeConverter converter = new ZonedDateTimeConverter();
    @Inject
    public HireService(HireDao hireDao) {
        this.hireDao = hireDao;
    }

    public List<TopHiresEntry> getTopHires() {
        return null;
    }

    public FreelancerHires getFreelancerHires(String freelancerId) {
        if (freelancerId == null) {
            throw new InvalidDataException("FreelancerId was null");
        }
        FreelancerHires freelancerHires = new FreelancerHires();
        freelancerHires.setNumHires(getHires(freelancerId).size());

        return freelancerHires;
    }

    public List<Hire> getHires(String freelancerId) {
        List<HireRecord> hires = hireDao.findByHireId(freelancerId);

        return hires.stream()
                .map(h -> new Hire(h.getFreelancerId(), h.getHireId(), new ZonedDateTimeConverter().convert(h.getDateHired())))
                .collect(Collectors.toList());
    }

    public HireResponse addHire(HireRequest hire) {
        if (hire == null || hire.getFreelancerId() == null || hire.getFreelancerId().length() == 0) {
            throw new InvalidDataException("Request must contain a valid Freelancer ID");
        }
        HireRecord record = new HireRecord();
        record.setFreelancerId(hire.getFreelancerId());
        record.setHireId(hire.getHireId());
        record.setDateHired(ZonedDateTime.now());
        hireDao.addHire(record);

        HireResponse response = new HireResponse();
        response.setHireId(record.getHireId());
        response.setFreelancerId(record.getFreelancerId());
        response.setHireDate(converter.convert(record.getDateHired()));

        return response;
    }

//    public ExampleData getExampleData(String id) {
//        List<HireRecord> records = hireDao.getExampleData(id);
//        if (records.size() > 0) {
//            return new ExampleData(records.get(0).getId(), records.get(0).getData());
//        }
//        return null;
//    }
//
//    public ExampleData setExampleData(String data) {
//        String id = UUID.randomUUID().toString();
//        HireRecord record = hireDao.setExampleData(id, data);
//        return new ExampleData(id, data);
//    }
}
