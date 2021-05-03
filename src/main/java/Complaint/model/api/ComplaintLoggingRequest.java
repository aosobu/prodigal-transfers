package Complaint.model.api;

import Complaint.model.Actor;
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

    private String initiator = "";
    private List<Complaint> complaints = new ArrayList<>();
    private List<MultipartFile> files = new ArrayList<>();
    //private BankService bankService;

    public String getInitiatorOrDefault(String defaultValue) {
        return Strings.isNullOrEmpty(initiator) ? defaultValue : initiator;
    }

    private Actor getActorDetials(){
        //get staff details using branch service;
        return null;
    }

    public static ComplaintLoggingRequest with(Complaint complaint) {
        ComplaintLoggingRequest request= new ComplaintLoggingRequest();
        request.setComplaints(Collections.singletonList(complaint));
        return request;
    }
}
