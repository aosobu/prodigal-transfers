package Complaint.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class BranchStatDateRange {
    private List<Branch> branches;
    private String dateRange;
}
