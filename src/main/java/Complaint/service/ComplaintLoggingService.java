package Complaint.service;

import Complaint.model.Complaint;
import Complaint.model.api.ComplaintLoggingRequest;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
public class ComplaintLoggingService {


    public List<Complaint> logComplaint(ComplaintLoggingRequest complaintLoggingRequest){

        // check for duplicate complaint done

        // generate tracking number: use a unique algorithm to generates a sequence done

        // update appropriate complaint states including approval statuses approval done, others pending

        // save complaint done

        // save complaint instruction. Confusing

        return new ArrayList<>();
    }

}
