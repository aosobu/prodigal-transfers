package Complaint.Service.complaintrequestprocessor;

import Complaint.model.Complaint;

import java.util.ArrayList;
import java.util.List;

public interface ComplaintRequestFilterProcessor {

    List<Complaint> process(List<Complaint> complaint) throws Exception;

//    default List<Complaint> process(List<Complaint> complaint, Complaint aComplaint) throws Exception {
//        return new ArrayList<>();
//    }
//
//    default Complaint processTwo(Complaint complaint) throws Exception {
//        return complaint;
//    }
//
//    default Boolean processThree(Complaint complaint) throws Exception {
//        return false;
//    }
}
