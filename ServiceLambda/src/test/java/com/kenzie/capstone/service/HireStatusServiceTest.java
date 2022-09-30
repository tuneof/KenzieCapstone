package com.kenzie.capstone.service;

import com.kenzie.capstone.service.dao.HireStatusDao;
import com.kenzie.capstone.service.model.HireStatus;
import com.kenzie.capstone.service.model.HireStatusRecord;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInstance;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.util.Arrays;
import java.util.List;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class HireStatusServiceTest {

    /** ------------------------------------------------------------------------
     *  expenseService.getExpenseById
     *  ------------------------------------------------------------------------ **/

    private HireStatusDao hireDao;
    private HireStatusService hireService;

    @BeforeAll
    void setup() {
        this.hireDao = mock(HireStatusDao.class);
        this.hireService = new HireStatusService(hireDao);
    }

    @Test
    void setHireStatusTest() {
        ArgumentCaptor<String> idCaptor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<String> statusCaptor = ArgumentCaptor.forClass(String.class);

        // GIVEN
        String status = "hired";

        // WHEN
        HireStatus response = this.hireService.setHireStatus(status);

        // THEN
        verify(hireDao, times(1)).setHireStatus(idCaptor.capture(), statusCaptor.capture());

        assertNotNull(idCaptor.getValue(), "An ID is generated");
        assertEquals(status, statusCaptor.getValue(), "The data is saved");

        assertNotNull(response, "A response is returned");
        assertEquals(idCaptor.getValue(), response.getFreelancerId(), "The response id should match");
        assertEquals(status, response.getStatus(), "The response data should match");
    }

    @Test
    void getHireStatusTest() {
        ArgumentCaptor<String> idCaptor = ArgumentCaptor.forClass(String.class);

        // GIVEN
        String id = "fakeid";
        String status = "hired";
        HireStatusRecord record = new HireStatusRecord();
        record.setFreelancerId(id);
        record.setStatus(status);


        when(hireDao.getHireStatus(id)).thenReturn(List.of(record));

        // WHEN
        HireStatus response = this.hireService.getHireStatus(id);

        // THEN
        verify(hireDao, times(1)).getHireStatus(idCaptor.capture());

        assertEquals(id, idCaptor.getValue(), "The correct id is used");

        assertNotNull(response, "A response is returned");
        assertEquals(id, response.getFreelancerId(), "The response id should match");
        assertEquals(status, response.getStatus(), "The response data should match");
    }
}