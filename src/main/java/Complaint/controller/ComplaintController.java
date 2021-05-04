package Complaint.controller;

import Complaint.Service.complaint.ComplaintService;
import Complaint.model.Complaint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/complaint/")
public class ComplaintController {

    @Autowired
    private ComplaintService complaintService;

    @PostMapping("create")
    public Complaint createComplaint (@RequestBody Complaint complaint) {
        return complaintService.createOrUpdateAComplaint(complaint);
    }

    @GetMapping("all")
    public List<Complaint> allComplaints() {
        return complaintService.getAllComplaints();
    }

    @GetMapping("{id}")
    public Complaint getAComplaint (@PathVariable Long id) {
        return complaintService.getAComplaint(id);
    }

    @PatchMapping("{id}")
    public Complaint updateComplaint (@PathVariable("id") Long id, @RequestBody Complaint complaint) {
        return complaintService.createOrUpdateAComplaint(complaint);
    }

    @DeleteMapping("{id}")
    public void deleteComplaint (@PathVariable Long id) {
        complaintService.deleteAComplaint(id);
    }
}