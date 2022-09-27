package com.kenzie.capstone.service;

import com.kenzie.capstone.service.dao.HireStatusDao;
import com.kenzie.capstone.service.model.HireStatus;
import com.kenzie.capstone.service.model.HireStatusRecord;

import javax.inject.Inject;
import java.util.List;
import java.util.UUID;

public class HireStatusService {
    private HireStatusDao hireStatusDao;

    @Inject
    public HireStatusService(HireStatusDao hireStatusDao) {
        this.hireStatusDao = hireStatusDao;
    }

    public HireStatus getHireStatus(String freelancerId) {
        List<HireStatusRecord> records = hireStatusDao.getHireStatus(freelancerId);
        if (records.size() > 0) {
            return new HireStatus(records.get(0).getFreelancerId(), records.get(0).getHireStatusId(), records.get(0).getStatus());
        }

        return null;
    }

//    public HireStatus setHireStatus(String freelancerId, String status) {
//        String id = UUID.randomUUID().toString();
//        HireStatusRecord record = hireStatusDao.setHireStatus(freelancerId, status);
//        return new HireStatus(freelancerId, id, status);
//    }
}
