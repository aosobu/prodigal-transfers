package Complaint.service.complaintrequestprocessor;

import Complaint.model.Complaint;
import Complaint.repository.ComplaintRepository;
import Complaint.service.ComplaintServiceImpl;
import Complaint.utilities.DatesUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

@Service
public class SaveComplaintFilter{

    ComplaintServiceImpl complaintServiceImpl;

    @Transactional
    public List<Complaint> process(List<Complaint> complaints) throws Exception {
        List<Complaint> complaintList = new ArrayList<>();
        Complaint savedComplaint = new Complaint();

        for (Complaint aComplaint : complaints) {
                setDateTimes(aComplaint);
                savedComplaint = complaintServiceImpl.saveComplaint(aComplaint);
                if(savedComplaint.getId() > 0){
                    complaintList.add(savedComplaint);
                }
        }
        return complaintList;
    }

    private void setDateTimes(Complaint aComplaint){
        aComplaint.setCreatedTime(aComplaint.getCreatedTime() == null || aComplaint.getCreatedTime().isEmpty()
                ? DatesUtils.getInstantDateTime() : aComplaint.getCreatedTime());

        aComplaint.setUpdatedTime(aComplaint.getUpdatedTime() == null || aComplaint.getUpdatedTime().isEmpty()
                ? DatesUtils.getInstantDateTime() : DatesUtils.getInstantDateTime());
    }

    @Autowired
    public void setComplaintServiceImpl(ComplaintServiceImpl complaintServiceImpl) {
        this.complaintServiceImpl = complaintServiceImpl;
    }
}
