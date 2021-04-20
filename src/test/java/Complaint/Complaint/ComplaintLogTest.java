package Complaint.Complaint;

import Complaint.Controller.ComplaintController;
import Complaint.Request.ComplaintLogRequest;
import Complaint.Service.ComplaintTransactionService;
import Complaint.Service.CustomerComplaintService;
import org.json.simple.JSONObject;
import org.junit.jupiter.api.Test;
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

@ContextConfiguration(classes = {ComplaintController.class,ComplaintLogRequest.class,CustomerComplaintService.class,ComplaintTransactionService.class })
@WebMvcTest
@AutoConfigureMockMvc
public class ComplaintLogTest {
    @MockBean
    private CustomerComplaintService customerComplaintService;
    @MockBean
    private ComplaintTransactionService complaintTransactionService;
    @Autowired
    MockMvc mockmvc;


    @Test
    void testCreateAccount() {
        ComplaintLogRequest complaintLogRequest = new ComplaintLogRequest();
        JSONObject obj = new JSONObject();
        obj.put("customer_id","C00002");
        obj.put("transactionId","T00002");
        obj.put("complaint_message","I made a mistake with sending my transaction to one Mr. Shark Frank");
        obj.put("createdBy","Test user-staff");
        obj.put("updatedBy","none");

       testGetAccount_statement(obj);
    }
    private void testGetAccount_statement(JSONObject obj) {

        try {
            MvcResult mvcr = mockmvc.perform(MockMvcRequestBuilders
                    .post("/complaint/log").content(obj.toString())
                    .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                    .andDo(print()).andReturn();
            mvcr.getResponse();
            assertNotNull(mvcr.getResponse());
            assertEquals(200, mvcr.getResponse().getStatus());
        } catch (Exception e) {
            e.getMessage();
        }
    }
}
