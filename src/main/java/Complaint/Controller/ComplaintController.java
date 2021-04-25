package Complaint.Controller;

import Complaint.ApiRequestModel.ComplaintLogRequest;
import Complaint.ApiRequestModel.ComplaintTransLogRequest;
import Complaint.DTO.ComplaintTransferQueryModel;
import Complaint.Entity.CustomerComplaint;
import Complaint.Entity.TransactionComplaint;
import Complaint.Enum.ComplaintState;
import Complaint.Enum.TransferRecallType;
import Complaint.Service.ComplaintTransactionService;
import Complaint.Service.ComplaintTransferService;
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
    private ComplaintTransferService complaintTransferService;

    @Autowired
    public ComplaintController(CustomerComplaintService customerComplaintService, ComplaintTransactionService complaintTransactionService,ComplaintTransferService complaintTransferService){
        this.customerComplaintService = customerComplaintService;
        this.complaintTransactionService = complaintTransactionService;
        this.complaintTransferService = complaintTransferService;
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
            return new ResponseEntity<>("Transaction Not Found: "+complaintTransLogRequest.getTransactionId(),HttpStatus.NOT_FOUND);
        }
        log.info("Transaction with ID: {} was found.",transactionComplaint.getTransaction_id());

        String queryResult = customerComplaintService.getTransComplaintMessageAndTransferTypeByTid(transactionComplaint.getTransaction_id());

        if(queryResult==null){
            return new ResponseEntity<>("No Transaction recall reason logged by customer",HttpStatus.NOT_FOUND);
        }
        String[] splitted_string = queryResult.split(",");

        String complaint_message, transfer_type;

        complaint_message = splitted_string[0].trim();
        transfer_type = splitted_string[1].trim();

        log.info("Customer Transaction recall reason: {}",complaint_message);
        log.info("Bank staff Transaction recall reason: {}",complaintTransLogRequest.getRecall_reason());

        if( complaintTransLogRequest.getTransfer_type().equalsIgnoreCase(transfer_type) && transfer_type.equalsIgnoreCase(TransferRecallType.INTRA.name())){

            //Trigger the email service to send email notification for INWARD TRANSACTION RECALL
            // Perform Lien operations

            log.info("Email sent for INTRA Bank transfer.");
            return  new ResponseEntity<>("Email sent for INTRA Bank transfer.",HttpStatus.OK);
        }else if( complaintTransLogRequest.getTransfer_type().equalsIgnoreCase(transfer_type) && transfer_type.equalsIgnoreCase(TransferRecallType.INTER.name())){

            //Trigger the email service to send email notification for OUTWARD TRANSACTION RECALL
            // Perform post email response Operations

            log.info("Email sent for INTER Bank transfer.");
            return  new ResponseEntity<>("Email sent for INTER Bank transfer.",HttpStatus.OK);
        }else{
            return new ResponseEntity<>("Invalid transfer type", HttpStatus.BAD_REQUEST);
        }
    }
    @GetMapping("/transfer")
    public ResponseEntity<?> getcomplaintTransfer(@RequestParam("customer_id") String customer_id, @RequestParam("transaction_id") String transaction_id){
      if(customer_id.isEmpty() || transaction_id.isEmpty()){
          return new ResponseEntity<>("Error with passed information",HttpStatus.BAD_REQUEST);
        }
        ComplaintTransferQueryModel complaintTransferQueryModel = complaintTransferService.getComplaintTransferDetails(customer_id,transaction_id);
      if(complaintTransferQueryModel == null){
          return new ResponseEntity<>("Transfer Details not found.",HttpStatus.NOT_FOUND);
      }
        return new ResponseEntity<>(complaintTransferQueryModel,HttpStatus.OK);
    }
}
