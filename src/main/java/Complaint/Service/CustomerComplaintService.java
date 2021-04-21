package Complaint.Service;

import Complaint.Entity.CustomerComplaint;
import Complaint.Repository.CustomerComplaintRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;

@Service
@Transactional
public class CustomerComplaintService {
    @Autowired
    private CustomerComplaintRepo customerComplaintRepo;

    public void SaveCustomerComplaint(CustomerComplaint customerComplaint){
        customerComplaintRepo.saveComplaint(customerComplaint.getCustomer_id(), customerComplaint.getComplaint_message(),
                customerComplaint.getCreatedBy(), customerComplaint.getCreatedDate(),customerComplaint.getComplaintState(), customerComplaint.getUpdatedBy());
    }
    public String getTransComplaintMessageByTid(String tid){
        return  customerComplaintRepo.getTransComplaintMessageByTid(tid);
    }

}
