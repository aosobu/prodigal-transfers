package Complaint.service;

import Complaint.model.Complaint;
import Complaint.model.api.ComplaintLoggingRequest;
import Complaint.service.complaintrequestprocessor.ComplaintRequestFilterProcessor;
import Complaint.utilities.SaveComplaint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class ComplaintLoggingService {

    private final Logger logger = LoggerFactory.getLogger(ComplaintLoggingService.class);
    private List<ComplaintRequestFilterProcessor> complaintRequestFilterProcessors;

    @SaveComplaint
    public List<Complaint> logComplaint(ComplaintLoggingRequest complaintLoggingRequest){

        List<Complaint> complaints = complaintLoggingRequest.getComplaints();

        for(ComplaintRequestFilterProcessor aComplaintRequestFilterProcessor : complaintRequestFilterProcessors){
            for(Complaint complaint: complaints) {
                try {
                    if(aComplaintRequestFilterProcessor.isApplicable(complaint)) {
                        aComplaintRequestFilterProcessor.process(complaint, complaintLoggingRequest.getMultipartFiles());
                    }
                } catch (Exception e) {
                    logger.info("Error executing a complaint request filter processor {} " +
                            aComplaintRequestFilterProcessor.getComplaintRequestFilterProcessorName());
                }
            }
        }

        complaintLoggingRequest.setComplaints(complaints);
        return complaintLoggingRequest.getComplaints();
    }

    @Resource(name = "complaintRequestFilterProcessor")
    public void setComplaintRequestFilterProcessors(List<ComplaintRequestFilterProcessor> complaintRequestFilterProcessors) {
        this.complaintRequestFilterProcessors = complaintRequestFilterProcessors;
    }
}
