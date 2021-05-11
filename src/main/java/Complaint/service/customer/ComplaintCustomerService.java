package Complaint.service.customer;

import Complaint.model.ComplaintCustomer;
import java.util.List;

public interface ComplaintCustomerService {

    List<ComplaintCustomer> getAllCustomers();

    ComplaintCustomer getACustomer(Long complaintCustomerId);

    ComplaintCustomer createOrUpdateACustomer(ComplaintCustomer complaintCustomer);

    void deleteACustomer(Long complaintCustomerId);
}
