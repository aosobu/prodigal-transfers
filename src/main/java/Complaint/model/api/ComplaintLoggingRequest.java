package Complaint.model.api;

import Complaint.model.BranchUser;
import Complaint.model.Complaint;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.assertj.core.util.Strings;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ComplaintLoggingRequest {

    private List<Complaint> complaints = new ArrayList<>();
    private List<MultipartFile> files = new ArrayList<>();
    private BranchUser branchUser;
    private String initiator;

    public static ComplaintLoggingRequest with(Complaint complaint, BranchUser branchUser, String defaultValue) {
        String initiator = "";
        ComplaintLoggingRequest request= new ComplaintLoggingRequest();
        request.setComplaints(Collections.singletonList(complaint));
        request.setBranchUser(branchUser);
        request.setInitiator(Strings.isNullOrEmpty(initiator) ? defaultValue : initiator);
        return request;
    }
}
