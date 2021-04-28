package Complaint.controller;

import Complaint.apimodel.request.ComplaintTransactionLogRequest;
import Complaint.apimodel.request.ComplaintCustomerLogRequest;
import Complaint.dao.ComplaintTransferQueryModel;
import Complaint.entity.ComplaintCustomer;
import Complaint.entity.ComplaintTransaction;
import Complaint.enums.TransferRecallType;
import Complaint.service.ComplaintService;
import Complaint.service.ComplaintTransactionService;
import Complaint.service.CustomerComplaintService;
import com.teamapt.exceptions.ApiException;
import com.teamapt.moneytor.lib.common.customer.model.CustomerAccount;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;

@Slf4j
@RestController
@RequestMapping("/api/v1/customer-complaint")
public class ComplaintController {

    private CustomerComplaintService customerComplaintService;
    private ComplaintTransactionService complaintTransactionService;
    private ComplaintService complaintService;
    @Autowired
    public ComplaintController(CustomerComplaintService customerComplaintService, ComplaintTransactionService complaintTransactionService){
        this.customerComplaintService = customerComplaintService;
        this.complaintTransactionService = complaintTransactionService;
        this.complaintTransactionService = complaintTransactionService;
    }
    @PostMapping("/log")
    public ResponseEntity<?> logCustomerComplaint(@RequestBody ComplaintCustomerLogRequest complaintCustomerLogRequest) throws ApiException {
        if(complaintCustomerLogRequest ==null | complaintCustomerLogRequest.getCustomer_id().isEmpty() | complaintCustomerLogRequest.getComplaint_message().isEmpty()
        | complaintCustomerLogRequest.getCreatedBy().isEmpty() | complaintCustomerLogRequest.getTransactionId().isEmpty()){
            log.info("Incomplete requested information.");
            return new ResponseEntity<>("Incomplete requested information.", HttpStatus.BAD_REQUEST);
        }
        // Verify customer existence
        CustomerAccount customerAccount = customerComplaintService.findCustomerAccountByID(complaintCustomerLogRequest.getCustomer_id());
        if(customerAccount == null){
            throw new ApiException("Unable to retrieve customer's account.");
        }
        Date created_date = new Date(new java.util.Date().getTime());
        ComplaintCustomer complaintCustomer = new ComplaintCustomer();
        complaintCustomer.setCustomerId("");

        log.info("Logging complaint for customer {}", complaintCustomer.getCustomerId());
        customerComplaintService.SaveCustomerComplaint(complaintCustomer);
        log.info("Logged complaint for customer success");


        log.info("Logging complaint transaction for customer {}", complaintCustomerLogRequest.getCustomer_id());
        ComplaintTransaction complaintTransaction = new ComplaintTransaction();
        complaintTransactionService.saveTransactionComplaint(complaintTransaction);
        log.info("Logged complaint transaction for customer success");

        return new ResponseEntity<>("Customer's Transaction Complaint logged Successfully.", HttpStatus.OK);
    }
    @PostMapping("/transaction/recall/request")
    public ResponseEntity<?> logTransactionRecall(@RequestBody ComplaintTransactionLogRequest complaintTransactionLogRequest) throws ApiException {

        if(complaintTransactionLogRequest ==null){
            throw new ApiException("Error with passed information",HttpStatus.BAD_REQUEST.value());
        }
        //All customer's transactions should be queried from Profectus as called from Finacle.
        // And then get the specific complaint transaction
       ComplaintTransaction complaintTransaction = null; /*complaintTransactionService.findByTransID(complaintTransactionLogRequest.getTransactionId());*/

        if (complaintTransaction ==null){
            throw  new ApiException("Transaction Not Found: ",HttpStatus.NOT_FOUND.value());
        }
        log.info("Transaction with ID: {} was found.", complaintTransaction.getTranId());

        String queryResult = customerComplaintService.getTransComplaintMessageAndTransferTypeByTid(complaintTransaction.getTranId());

        if(queryResult==null){
            throw new ApiException("No Transaction recall reason logged by customer",HttpStatus.NOT_FOUND.value());
        }
        String[] splitted_string = queryResult.split(",");

        String complaint_message, transfer_type;

        complaint_message = splitted_string[0].trim();
        transfer_type = splitted_string[1].trim();

        log.info("Customer Transaction recall reason: {}",complaint_message);
        log.info("Bank staff Transaction recall reason: {}", complaintTransactionLogRequest.getRecall_reason());

        if( complaintTransactionLogRequest.getTransfer_type().equalsIgnoreCase(transfer_type) && transfer_type.equalsIgnoreCase(TransferRecallType.INTRA.name())){

            //Trigger the email service to send email notification for INWARD TRANSACTION RECALL
            // Perform Lien operations

            log.info("Email sent for INTRA Bank transfer.");
            return  new ResponseEntity<>("Email sent for INTRA Bank transfer.",HttpStatus.OK);
        }else if( complaintTransactionLogRequest.getTransfer_type().equalsIgnoreCase(transfer_type) && transfer_type.equalsIgnoreCase(TransferRecallType.INTER.name())){

            //Trigger the email service to send email notification for OUTWARD TRANSACTION RECALL
            // Perform post email response Operations

            log.info("Email sent for INTER Bank transfer.");
            return  new ResponseEntity<>("Email sent for INTER Bank transfer.",HttpStatus.OK);
        }else{
            return new ResponseEntity<>("Invalid transfer type", HttpStatus.BAD_REQUEST);
        }
    }
    @GetMapping("/transfer")
    public ResponseEntity<?> getcomplaintTransfer(@RequestParam("customer_id") String customer_id, @RequestParam("transaction_id") String transaction_id) throws ApiException {
      if(customer_id.isEmpty() || transaction_id.isEmpty()){
          return new ResponseEntity<>("Error with passed information",HttpStatus.BAD_REQUEST);
        }
        ComplaintTransferQueryModel complaintTransferQueryModel = null; /*complaintTransactionService.getComplaintTransferDetails(customer_id,transaction_id);*/
      if(complaintTransferQueryModel == null){
          throw  new ApiException("Transfer Details not found.",HttpStatus.NOT_FOUND.value());
      }
        return new ResponseEntity<>(complaintTransferQueryModel,HttpStatus.OK);
    }
}
