package Complaint.service;

import Complaint.model.Complaint;
import Complaint.model.ComplaintTransaction;
import Complaint.model.Transaction;
import Complaint.model.api.ComplaintLoggingRequest;
import Complaint.repository.ComplaintTransactionRepository;
import Complaint.service.complaintrequestprocessor.ComplaintRequestFilterProcessor;
import Complaint.utilities.SaveComplaint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ComplaintLoggingService {

    private final Logger logger = LoggerFactory.getLogger(ComplaintLoggingService.class);

    private ComplaintTransactionRepository complaintTransactionRepository;
    private List<ComplaintRequestFilterProcessor> complaintRequestFilterProcessors;

    @SaveComplaint
    public List<Complaint> logComplaint(ComplaintLoggingRequest complaintLoggingRequest){

        List<Complaint> complaints = complaintLoggingRequest.getComplaints();

        for(ComplaintRequestFilterProcessor aComplaintRequestFilterProcessor : complaintRequestFilterProcessors){
            for(Complaint complaint: complaints) {
                try {
                    aComplaintRequestFilterProcessor.process(complaint, complaintLoggingRequest.getMultipartFiles());
                } catch (Exception e) {
                    logger.info("Error executing a complaint request filter processor {} " + e.getMessage());
                }
            }
        }

        return complaints;
    }

    public List<Transaction> findAlreadyLoggedTxns(List<Transaction> transactions) {
        List<ComplaintTransaction> matchingComplaints;

        for (Transaction txn : transactions) {
            matchingComplaints =  complaintTransactionRepository.findByAccountNumberAndTranIdAndAmountAndTransactionDate(
                    txn.getAccountNumber(),
                    txn.getTranId(),
                    txn.getAmount(),
                    txn.getTransactionDate()
            );

            if (!matchingComplaints.isEmpty())
                txn.setAlreadyLoggedComplaint(matchingComplaints.get(0));
        }

        return transactions;
    }

    @Autowired
    public void setComplaintTransactionRepository(ComplaintTransactionRepository complaintTransactionRepository) {
        this.complaintTransactionRepository = complaintTransactionRepository;
    }
}
