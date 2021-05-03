package Complaint.Service.customer;

import Complaint.model.ComplaintCustomer;
import Complaint.repository.ComplaintCustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class ComplaintCustomerServiceImpl implements ComplaintCustomerService{

    @Autowired
    private ComplaintCustomerRepository complaintCustomerRepository;

    @Override
    public List<ComplaintCustomer> getAllCustomers() {
        return complaintCustomerRepository.findAll();
    }

    @Override
    public ComplaintCustomer getACustomer(Long complaintCustomerId) {
        return complaintCustomerRepository.findById(complaintCustomerId).orElse(null);
    }

    @Override
    public ComplaintCustomer createOrUpdateACustomer(ComplaintCustomer complaintCustomer) {
        return complaintCustomerRepository.save(complaintCustomer);
    }

    @Override
    public void deleteACustomer(Long complaintCustomerId) {
        complaintCustomerRepository.deleteById(complaintCustomerId);
    }
}
