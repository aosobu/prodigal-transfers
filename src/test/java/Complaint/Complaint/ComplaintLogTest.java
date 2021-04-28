package Complaint.Complaint;

import Complaint.apimodel.request.ComplaintCustomerLogRequest;
import Complaint.apimodel.request.ComplaintTransactionLogRequest;
import Complaint.controller.ComplaintController;
import Complaint.entity.ComplaintCustomer;
import Complaint.entity.ComplaintTransaction;
import Complaint.enums.ComplaintState;
import Complaint.repository.ComplaintCustomerRepository;
import Complaint.service.ComplaintTransactionService;
import Complaint.service.CustomerComplaintService;
import org.json.simple.JSONObject;
import org.junit.Before;
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

import java.sql.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@ContextConfiguration(classes = {ComplaintController.class, ComplaintCustomerLogRequest.class, ComplaintTransactionLogRequest.class,ComplaintTransactionService.class})
@WebMvcTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ComplaintLogTest {
    @MockBean
    private ComplaintTransactionLogRequest complaintTransactionLogRequest;
    @MockBean
    private CustomerComplaintService customerComplaintService;
    @MockBean
    private ComplaintTransactionService complaintTransactionService ;
    @MockBean
    private ComplaintCustomerRepository complaintCustomerRepository;
    /*@MockBean
    public ComplaintTransaction transactionComplaint;*/
    @MockBean
    private ComplaintCustomer complaintCustomer;

    MockMvc mockmvc;
    private ComplaintState complaintState;

    @Autowired
    public ComplaintLogTest(MockMvc mockmvc){ this.mockmvc = mockmvc; }
    @Before
    public void initTransactionlog(){
        Date created_date = new Date(new java.util.Date().getTime());
        //ComplaintCustomer complaintCustomer = new ComplaintCustomer(0L, "C00001","I made a mistake with sending my transaction to one Mr. Shark Frank" , created_date, "Jernice Lee", "Maria Anderson", ComplaintState.NEW.toString());
        customerComplaintService.SaveCustomerComplaint(complaintCustomer);

        ComplaintTransaction complaintTransaction = new ComplaintTransaction();
        complaintTransactionService.saveTransactionComplaint(complaintTransaction);
        //Get returned transaction from DB
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

    public void testLogCustomerComplaint(JSONObject obj) {

        try {
            MvcResult mvcr = mockmvc.perform(MockMvcRequestBuilders
                    .post("/api/v1/customer-complaint/log")
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

    @Test
    @Order(2)
    void testComplaintTransLog (){
        JSONObject obj = new JSONObject();
        obj.put("transactionId","T00001");
        obj.put("transfer_type","inter");
        obj.put("recall_reason","Made a mistake. Wrong transaction.");
        testComplaintTransLogRequest(obj);
    }
    public void testComplaintTransLogRequest(JSONObject obj){
        try {
            MvcResult mvcr = mockmvc.perform(MockMvcRequestBuilders
                    .post("/api/v1/customer-complaint/transaction/recall/request")
                    .content(obj.toJSONString())
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
}
