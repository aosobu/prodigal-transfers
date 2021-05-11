package Complaint.Service.complaintrequestprocessor;

import Complaint.model.Complaint;

import java.util.List;

public class LienPlacedFilter implements ComplaintRequestFilterProcessor{
    @Override
    public List<Complaint> process(List<Complaint> complaint) throws Exception {

        for (Complaint complaint1 : complaint) {

            if (!(complaint1.getComplaintState().getIsLienPlaced() == null ||
                    complaint1.getComplaintState().getIsLienPlaced())) {
                complaint1.getComplaintState().setIsLienPlaced(true);
            }
        }
        return complaint;
    }
}
