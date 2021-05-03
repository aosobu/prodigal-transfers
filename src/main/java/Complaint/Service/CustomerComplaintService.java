package Complaint.service;

import Complaint.repository.ComplaintCustomerRepository;
import com.teamapt.moneytor.lib.common.customer.model.CustomerAccount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class CustomerComplaintService {
    @Autowired
    private ComplaintCustomerRepository complaintCustomerRepository;

    public void SaveCustomerComplaint(ComplaintCustomer complaintCustomer){
        complaintCustomerRepository.saveComplaint(complaintCustomer.getCustomer_id(), complaintCustomer.getComplaint_message(),
                complaintCustomer.getCreatedBy(), complaintCustomer.getCreatedDate(), complaintCustomer.getComplaintState(), complaintCustomer.getUpdatedBy());
    }
    public String getTransComplaintMessageAndTransferTypeByTid(String tid){
        return  complaintCustomerRepository.getTransComplaintMessageAndTransferTypeByTid(tid);
    }

    public CustomerAccount findCustomerAccountByID(String customer_id){
        return complaintCustomerRepository.findCustomerAccountByID(customer_id);
    }

}
