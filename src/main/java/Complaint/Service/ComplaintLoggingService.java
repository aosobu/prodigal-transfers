package Complaint.Service;

import Complaint.Service.complaintrequestprocessor.ComplaintRequestFilterProcessor;
import Complaint.model.Complaint;
import Complaint.model.api.ComplaintLoggingRequest;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class ComplaintLoggingService {


    public List<Complaint> logComplaint(ComplaintLoggingRequest complaintLoggingRequest){

        // check for duplicate complaint

        // generate tracking number: use a unique algorithm to generates a sequence

        // update appropriate complaint states including approval statuses

        // save complaint

        // save complaint instruction

        return new ArrayList<>();
    }

}
