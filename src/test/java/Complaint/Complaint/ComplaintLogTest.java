package Complaint.Complaint;

import Complaint.Controller.ComplaintController;
import Complaint.Entity.TransactionComplaint;
import Complaint.Request.ComplaintLogRequest;
import Complaint.Request.ComplaintTransLogRequest;
import Complaint.Service.ComplaintTransactionService;
import Complaint.Service.CustomerComplaintService;
import org.json.simple.JSONObject;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@ContextConfiguration(classes = {ComplaintController.class,ComplaintLogRequest.class, ComplaintTransLogRequest.class, TransactionComplaint.class})
@WebMvcTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ComplaintLogTest {
    @MockBean
    private CustomerComplaintService customerComplaintService;
    @MockBean
    private ComplaintTransactionService complaintTransactionService;
    MockMvc mockmvc;
    @MockBean
    private TransactionComplaint transactionComplaint;

    @Autowired
    public ComplaintLogTest(MockMvc mockmvc){
       this.mockmvc = mockmvc;
    }
    @Test
    @Order(1)
    void testComplaintLog() {
        JSONObject obj = new JSONObject();
        obj.put("customer_id","C00001");
        obj.put("transactionId","T00001");
        obj.put("complaint_message","I made a mistake with sending my transaction to one Mr. Shark Frank");
        obj.put("createdBy","Test user-staff");
        obj.put("updatedBy","none");

        testLogCustomerComplaint(obj);
    }
    @Test
    @Order(2)
    void testComplaintTransLog (){

        /*transactionComplaint = new TransactionComplaint(null,"C00001","T00001","inter");
        complaintTransactionService.saveTransactionComplaint(transactionComplaint);*/
        JSONObject obj = new JSONObject();
        obj.put("transactionId","T00001");
        obj.put("transfer_type","inter");
        obj.put("recall_reason","Made a mistake Wrong transaction.");

        testComplaintTransLogRequest(obj);
    }


    private void testLogCustomerComplaint(JSONObject obj) {

        try {
            MvcResult mvcr = mockmvc.perform(MockMvcRequestBuilders
                    .post("/complaint/log")
                    .content(obj.toString())
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON))
                    .andDo(print())
                    .andReturn();
            mvcr.getResponse();
            assertNotNull(mvcr.getResponse());
            assertEquals(200, mvcr.getResponse().getStatus());
        } catch (Exception e) {
            e.getMessage();
        }
    }

    private void testComplaintTransLogRequest(JSONObject obj){
        try {
            MvcResult mvcr = mockmvc.perform(MockMvcRequestBuilders
                    .post("/complaint/trans/recall/request")
                    .content(obj.toString())
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON))
                    .andDo(print())
                    .andReturn();
            mvcr.getResponse();
            assertNotNull(mvcr.getResponse());
            assertEquals(404, mvcr.getResponse().getStatus());
        } catch (Exception e) {
            e.getMessage();
        }
    }
}
