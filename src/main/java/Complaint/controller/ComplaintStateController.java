package Complaint.controller;

import Complaint.Service.state.ComplaintStateService;
import Complaint.model.ComplaintState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/v1/state/")
public class ComplaintStateController {

    @Autowired
    private ComplaintStateService complaintStateService;

    @PostMapping("create")
    public ComplaintState createState (@RequestBody ComplaintState complaintState) {
        return complaintStateService.createOrUpdateAState(complaintState);
    }

    @GetMapping("all")
    public List<ComplaintState> allStates() {
        return complaintStateService.getAllStates();
    }

    @GetMapping("{id}")
    public ComplaintState getAState (@PathVariable Long id) {
        return complaintStateService.getAState(id);
    }

    @PatchMapping("{id}")
    public ComplaintState updateState (@PathVariable("id") Long id, @RequestBody ComplaintState complaintState) {
        return complaintStateService.createOrUpdateAState(complaintState);
    }

    @DeleteMapping("{id}")
    public void deleteState (@PathVariable Long id) {
        complaintStateService.deleteAState(id);
    }
}