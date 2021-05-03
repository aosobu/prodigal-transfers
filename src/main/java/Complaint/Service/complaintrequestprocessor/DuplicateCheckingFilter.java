package Complaint.Service.complaintrequestprocessor;

import Complaint.model.Complaint;

import java.util.List;

public class DuplicateCheckingFilter implements ComplaintRequestFilterProcessor {

    @Override
    public List<Complaint> process(List<Complaint> complaint) {
        return null;
    }
}
