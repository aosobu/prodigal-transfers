package Complaint.controller;

import Complaint.Service.customer.ComplaintCustomerService;
import Complaint.model.ComplaintCustomer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/customer/")
public class ComplaintCustomerController {

    @Autowired
    private ComplaintCustomerService complaintCustomerService;

    @PostMapping("create")
    public ComplaintCustomer createCustomer(@RequestBody ComplaintCustomer complaintCustomer) {
        return complaintCustomerService.createOrUpdateACustomer(complaintCustomer);
    }

    @GetMapping("all")
    public List<ComplaintCustomer> allCustomers() {
        return complaintCustomerService.getAllCustomers();
    }

    @GetMapping("{id}")
    public ComplaintCustomer getACustomer(@PathVariable Long id) {
        return complaintCustomerService.getACustomer(id);
    }

    @PatchMapping("{id}")
    public ComplaintCustomer updateCustomer(@PathVariable("id") Long id, @RequestBody ComplaintCustomer complaintCustomer) {
        return complaintCustomerService.createOrUpdateACustomer(complaintCustomer);
    }

    @DeleteMapping("{id}")
    public void deleteCustomer(@PathVariable Long id) {
        complaintCustomerService.deleteACustomer(id);
    }
}
