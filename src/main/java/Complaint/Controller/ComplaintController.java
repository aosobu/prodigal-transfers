package Complaint.Controller;

import Complaint.Entity.CustomerComplaint;
import Complaint.Entity.TransactionComplaint;
import Complaint.Enum.ComplaintState;
import Complaint.Request.ComplaintLogRequest;
import Complaint.Service.ComplaintTransactionService;
import Complaint.Service.CustomerComplaintService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
@Slf4j
@RestController
@RequestMapping("/complaint")
public class ComplaintController {
    @Autowired
    private CustomerComplaintService customerComplaintService;
    @Autowired
    private ComplaintTransactionService complaintTransactionService;
    @PostMapping("/log")
    public ResponseEntity<?> logComplaint(@RequestBody ComplaintLogRequest complaintLogRequest){
        if(complaintLogRequest ==null | complaintLogRequest.getCustomer_id().isEmpty() |complaintLogRequest.getComplaint_message().isEmpty()
        |complaintLogRequest.getCreatedBy().isEmpty() | complaintLogRequest.getTransactionId().isEmpty()){
            log.info("Incomplete requested information.");
            return new ResponseEntity<>("Incomplete requested information.", HttpStatus.BAD_REQUEST);
        }
        CustomerComplaint customerComplaint = new CustomerComplaint();

        customerComplaint.setCustomer_id(complaintLogRequest.getCustomer_id());
        customerComplaint.setComplaint_message(complaintLogRequest.getComplaint_message());
        customerComplaint.setCreatedBy(complaintLogRequest.getCreatedBy());
        Date created_date = new Date(new java.util.Date().getTime());
        customerComplaint.setCreatedDate(created_date);
        customerComplaint.setUpdatedBy(complaintLogRequest.getUpdatedBy());
        customerComplaint.setComplaintState(ComplaintState.NEW.toString());

        log.info("Logging complaint for customer {}",customerComplaint.getCustomer_id());
        customerComplaintService.SaveCustomerComplaint(customerComplaint);
        log.info("Logged complaint for customer success");

        log.info("Logging complaint transaction for customer {}",complaintLogRequest.getCustomer_id());
        TransactionComplaint transactionComplaint = new TransactionComplaint();
        transactionComplaint.setCustomer_id(complaintLogRequest.getCustomer_id());
        transactionComplaint.setTid(complaintLogRequest.getTransactionId());
        complaintTransactionService.saveTransactionComolaint(transactionComplaint);
        log.info("Logged complaint transaction for customer success");

        return new ResponseEntity<>("Customer's Transaction Complaint logged Successfully.", HttpStatus.OK);
    }
}
