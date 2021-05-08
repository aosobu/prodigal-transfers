package Complaint.Service.complaintrequestprocessor;

import Complaint.model.Complaint;

import java.util.List;

public interface ComplaintRequestFilterProcessor {

    List<Complaint> process(List<Complaint> complaint) throws Exception;

}
