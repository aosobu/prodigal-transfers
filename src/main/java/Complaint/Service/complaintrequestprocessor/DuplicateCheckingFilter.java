package Complaint.Service.complaintrequestprocessor;

import Complaint.model.Complaint;
import Complaint.repository.ComplaintRepository;
//import Complaint.service.complaintrequestprocessor.ComplaintRequestFilterProcessor;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class DuplicateCheckingFilter implements ComplaintRequestFilterProcessor {

    boolean isDuplicateComplaintObjectPresent;

    ComplaintRepository complaintRepository;

    @Override
    public List<Complaint> process(List<Complaint> complaint) throws Exception {

        List<Complaint> dataStoreComplaints = complaintRepository.findAll();
        Set<String> allRrns = new HashSet<>();
        Set<String> allTxnIds = new HashSet<>();
        Set<String> allAcctNums = new HashSet<>();

        for (Complaint complaint1 : dataStoreComplaints) {
            allRrns.add(complaint1.getTransaction().getRrn());
            allTxnIds.add(complaint1.getTransaction().getTranId());
            allAcctNums.add(complaint1.getCustomer().getCustomerAccountNumber());
        }

        for (Complaint complaint2 : complaint) {
            if (allRrns.contains(complaint2.getTransaction().getRrn()) ||
                    allTxnIds.contains(complaint2.getTransaction().getTranId()) ||
                    allAcctNums.contains(complaint2.getCustomer().getCustomerAccountNumber())) {
                isDuplicateComplaintObjectPresent = true;
            }
            else {
                isDuplicateComplaintObjectPresent = false;
            }
        }
        return complaint;
    }
}