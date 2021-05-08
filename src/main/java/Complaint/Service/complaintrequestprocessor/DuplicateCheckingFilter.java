package Complaint.Service.complaintrequestprocessor;

import Complaint.model.Complaint;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class DuplicateCheckingFilter implements ComplaintRequestFilterProcessor {

    boolean isDuplicateComplaintObjectPresent;

    @Override
    public List<Complaint> process(List<Complaint> complaint) throws Exception {

        try {
            Set<Complaint> complaintSet = new HashSet<>(complaint);
            isDuplicateComplaintObjectPresent = complaint.size() != complaintSet.size();
        }
        catch (Exception e) {
            throw new Exception("Action cannot be completed");
        }
        return complaint;
    }
}