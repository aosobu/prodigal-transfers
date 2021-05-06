package Complaint.Service.complaintrequestprocessor;

import Complaint.model.Complaint;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class DuplicateCheckingFilter implements ComplaintRequestFilterProcessor {

    @Override
    public List<Complaint> process(List<Complaint> complaint, Complaint aComplaint) throws Exception {

        Set<String> hashSet = new HashSet<>();

        for (Complaint complaint1 : complaint) {
            hashSet.add(complaint1.getTransaction().getRrn());
        }

        if (hashSet.contains(aComplaint.getTransaction().getRrn()))
            throw new Exception("This complaint has already been created");
        return complaint;
    }
}