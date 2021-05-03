package Complaint.controller;

import Complaint.Service.transaction.ComplaintTransactionService;
import Complaint.model.ComplaintTransaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/v1/transaction/")
public class ComplaintTransactionController {

    @Autowired
    private ComplaintTransactionService complaintTransactionService;

    @PostMapping("create")
    public ComplaintTransaction createTransaction(@RequestBody ComplaintTransaction complaintTransaction) {
        return complaintTransactionService.createOrUpdateATransaction(complaintTransaction);
    }

    @GetMapping("all")
    public List<ComplaintTransaction> allTransactions() {
        return complaintTransactionService.getAllTransactions();
    }

    @GetMapping("{id}")
    public ComplaintTransaction getATransaction(@PathVariable Long id) {
        return complaintTransactionService.getATransaction(id);
    }

    @PatchMapping("{id}")
    public ComplaintTransaction updateTransaction(@PathVariable("id") Long id,
                                                  @RequestBody ComplaintTransaction complaintTransaction) {
        return complaintTransactionService.createOrUpdateATransaction(complaintTransaction);
    }

    @DeleteMapping("{id}")
    public void deleteTransaction(@PathVariable Long id) {
        complaintTransactionService.deleteATransaction(id);
    }
}
