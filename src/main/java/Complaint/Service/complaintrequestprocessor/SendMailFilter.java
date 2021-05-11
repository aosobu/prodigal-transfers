package Complaint.Service.complaintrequestprocessor;

import Complaint.model.Complaint;

import java.util.List;

public class SendMailFilter implements ComplaintRequestFilterProcessor {

    @Override
    public List<Complaint> process(List<Complaint> complaint) throws Exception {

//        for (Complaint complaint1 : complaint) {
//
//            if (!(complaint1.getComplaintState().getIsMailSentToBankStaff() == null ||
//                    complaint1.getComplaintState().getIsMailSentToBankStaff())) {
//                complaint1.getComplaintState().setIsMailSentToBankStaff(true);
//            }
//        }
        return complaint;
    }
}
