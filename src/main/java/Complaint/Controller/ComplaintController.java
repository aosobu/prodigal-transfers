package Complaint.Controller;

import Complaint.Entity.CustomerComplaint;
import Complaint.Entity.TransactionComplaint;
import Complaint.Enum.ComplaintState;
import Complaint.Request.ComplaintLogRequest;
import Complaint.Request.ComplaintTransLogRequest;
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

    private CustomerComplaintService customerComplaintService;
    private ComplaintTransactionService complaintTransactionService;

    @Autowired
    public ComplaintController(CustomerComplaintService customerComplaintService, ComplaintTransactionService complaintTransactionService){
        this.customerComplaintService = customerComplaintService;
        this.complaintTransactionService = complaintTransactionService;
    }
    @PostMapping("/log")
    public ResponseEntity<?> logCustomerComplaint(@RequestBody ComplaintLogRequest complaintLogRequest){
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
        TransactionComplaint transactionComplaint = new TransactionComplaint(0L,complaintLogRequest.getCustomer_id(),complaintLogRequest.getTransactionId());
        complaintTransactionService.saveTransactionComplaint(transactionComplaint);
        log.info("Logged complaint transaction for customer success");

        return new ResponseEntity<>("Customer's Transaction Complaint logged Successfully.", HttpStatus.OK);
    }
    @PostMapping("/trans/recall/request")
    public ResponseEntity<?> logTransactionRecall(@RequestBody ComplaintTransLogRequest complaintTransLogRequest){

        if(complaintTransLogRequest ==null){
            return new ResponseEntity<>("Error with passed information",HttpStatus.BAD_REQUEST);
        }
        //All customer's transactions should be queried from Profectus as called from Finacle.
        // And then get the specific complaint transaction
        TransactionComplaint transactionComplaint =complaintTransactionService.findByTransID(complaintTransLogRequest.getTid());

        if (transactionComplaint==null){
            return new ResponseEntity<>("Transaction Not Found",HttpStatus.NOT_FOUND);
        }
        log.info("Transaction with ID: {} was found.",transactionComplaint.getTid());
        // get customer complaint message
        String complaint_message =customerComplaintService.getTransComplaintMessageByTid(transactionComplaint.getTid());
        if(complaint_message==null){
            return new ResponseEntity<>("No Transaction recall reason logged by customer",HttpStatus.NOT_FOUND);
        }

        log.info("Customer Transaction recall reason: {}",complaint_message);
        log.info("Bank staff Transaction recall reason: {}",complaintTransLogRequest.getRecall_reason());
        //Trigger the email service to send email notification as referenced in the Doc- Bank Operations No. 2
        return  new ResponseEntity<>("Email sent",HttpStatus.OK);
    }
}
