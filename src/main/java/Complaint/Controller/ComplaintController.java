package Complaint.Controller;

import Complaint.Entity.CustomerComplaint;
import Complaint.Entity.TransactionComplaint;
import Complaint.Enum.ComplaintState;
import Complaint.Enum.TransferRecallType;
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
        Date created_date = new Date(new java.util.Date().getTime());
        CustomerComplaint customerComplaint = new CustomerComplaint(0L, complaintLogRequest.getCustomer_id(), complaintLogRequest.getComplaint_message(), created_date, complaintLogRequest.getCreatedBy(), complaintLogRequest.getUpdatedBy(), ComplaintState.NEW.toString());

        log.info("Logging complaint for customer {}",customerComplaint.getCustomer_id());
        customerComplaintService.SaveCustomerComplaint(customerComplaint);
        log.info("Logged complaint for customer success");


        log.info("Logging complaint transaction for customer {}",complaintLogRequest.getCustomer_id());
        TransactionComplaint transactionComplaint = new TransactionComplaint(null,complaintLogRequest.getCustomer_id(),complaintLogRequest.getTransactionId(),complaintLogRequest.getTransferType());
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
        TransactionComplaint transactionComplaint =complaintTransactionService.findByTransID(complaintTransLogRequest.getTransactionId());

        if (transactionComplaint==null){
            return new ResponseEntity<>("Transaction Not Found",HttpStatus.NOT_FOUND);
        }
        log.info("Transaction with ID: {} was found.",transactionComplaint.getTransaction_id());
        // get customer complaint message
        String complaint_message =customerComplaintService.getTransComplaintMessageByTid(transactionComplaint.getTransaction_id());
        if(complaint_message==null){
            return new ResponseEntity<>("No Transaction recall reason logged by customer",HttpStatus.NOT_FOUND);
        }

        log.info("Customer Transaction recall reason: {}",complaint_message);
        log.info("Bank staff Transaction recall reason: {}",complaintTransLogRequest.getRecall_reason());
        if(transactionComplaint.getTransfer_type().equalsIgnoreCase(complaintTransLogRequest.getTransfer_type()) && transactionComplaint.getTransfer_type().equalsIgnoreCase(TransferRecallType.INTRA.name())){
        //Trigger the email service to send email notification for INWARD TRANSACTION RECALL
            // Perform Lien operations
            log.info("Email sent for INTRA Bank transfer.");
        }else{
            new ResponseEntity<>("Invalid transfer type", HttpStatus.BAD_REQUEST);
        }
        if(transactionComplaint.getTransfer_type().equalsIgnoreCase(complaintTransLogRequest.getTransfer_type()) && transactionComplaint.getTransfer_type().equalsIgnoreCase(TransferRecallType.INTER.name())){
            //Trigger the email service to send email notification for OUTWARD TRANSACTION RECALL
            // Perform post email response Operations
            log.info("Email sent for INTER Bank transfer.");
        }else{
            new ResponseEntity<>("Invalid transfer type", HttpStatus.BAD_REQUEST);
        }

        return  new ResponseEntity<>("Email sent",HttpStatus.OK);
    }
}
