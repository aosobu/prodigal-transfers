package Complaint.Service;

import Complaint.model.Complaint;
import Complaint.model.api.ComplaintLoggingRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ComplaintLoggingService {

    public List<Complaint> logComplaint(ComplaintLoggingRequest complaintLoggingRequest){

        // check for duplicate complaint

        // generate tracking number: use a unque algorithm to generates a sequence

        // save complaint

        // save complaint instruction

        // update appropriate complaint states

        return new ArrayList<>();
    }

}
