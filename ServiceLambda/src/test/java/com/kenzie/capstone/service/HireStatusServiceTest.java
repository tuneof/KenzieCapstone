package com.kenzie.capstone.service;

import com.kenzie.capstone.service.dao.HireStatusDao;
import com.kenzie.capstone.service.model.HireStatus;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInstance;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

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

//    @Test
//    void setDataTest() {
//        ArgumentCaptor<String> idCaptor = ArgumentCaptor.forClass(String.class);
//        ArgumentCaptor<String> dataCaptor = ArgumentCaptor.forClass(String.class);
//
//        // GIVEN
//        String data = "somedata";
//
//        // WHEN
//        //ExampleData response = this.hireService.setExampleData(data);
//
//        // THEN
//        //verify(hireDao, times(1)).setExampleData(idCaptor.capture(), dataCaptor.capture());
//
//        assertNotNull(idCaptor.getValue(), "An ID is generated");
//        assertEquals(data, dataCaptor.getValue(), "The data is saved");
//
//        assertNotNull(response, "A response is returned");
//        assertEquals(idCaptor.getValue(), response.getId(), "The response id should match");
//        assertEquals(data, response.getData(), "The response data should match");
//    }
//
//    @Test
//    void getDataTest() {
//        ArgumentCaptor<String> idCaptor = ArgumentCaptor.forClass(String.class);
//
//        // GIVEN
//        String id = "fakeid";
//        String data = "somedata";
//        HireRecord record = new HireRecord();
//        record.setId(id);
//        record.setData(data);
//
//
//        when(hireDao.getExampleData(id)).thenReturn(Arrays.asList(record));
//
//        // WHEN
//        ExampleData response = this.hireService.getExampleData(id);
//
//        // THEN
//        verify(hireDao, times(1)).getExampleData(idCaptor.capture());
//
//        assertEquals(id, idCaptor.getValue(), "The correct id is used");
//
//        assertNotNull(response, "A response is returned");
//        assertEquals(id, response.getId(), "The response id should match");
//        assertEquals(data, response.getData(), "The response data should match");
//    }

    // Write additional tests here

}