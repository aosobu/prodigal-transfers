package Complaint.service;

import Complaint.model.Complaint;
import Complaint.model.api.ComplaintLoggingRequest;
import Complaint.service.complaintrequestprocessor.ComplaintRequestFilterProcessor;
import Complaint.service.complaintrequestprocessor.SaveComplaintFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class ComplaintLoggingService {

    private final Logger logger = LoggerFactory.getLogger(ComplaintLoggingService.class);

    private List<ComplaintRequestFilterProcessor> complaintRequestFilterProcessors;
    private SaveComplaintFilter saveComplaintFilter;
    private ComplaintServiceImpl complaintService;

    public List<Complaint> logComplaint(ComplaintLoggingRequest complaintLoggingRequest) throws Exception{
        List<Complaint> complaints;
        String processorMessage = "", messagePartA = "Complaint size of ", messagePartB = " successfully saved {} ", messagePartC = " not successfully saved {} ";
        boolean processorStatus = true;

        complaintLoggingRequest.setComplaints(saveComplaintFilter.process(complaintLoggingRequest.getComplaints()));
        complaints = complaintLoggingRequest.getComplaints();

        for (ComplaintRequestFilterProcessor aComplaintRequestFilterProcessor : complaintRequestFilterProcessors) {
            for (Complaint complaint : complaints) {
                try{
                    if (aComplaintRequestFilterProcessor.isApplicable(complaint)) {
                        aComplaintRequestFilterProcessor.process(complaint, complaintLoggingRequest.getMultipartFiles());
                    }
                }catch(Exception ex) {
                    processorStatus = false;
                    processorMessage = processorMessage.concat(String.format("%s%s\\n", " Error Encountered while executing filter {} ",
                                                        aComplaintRequestFilterProcessor.getComplaintRequestFilterProcessorName()));
                }
            }
        }

        if(updateComplaints(processorStatus, complaints)){
            return finalize(complaintLoggingRequest, complaints, messagePartA, messagePartB);
        }else{
            for(Complaint complaint: complaints){
                complaint.setErrorMessage(processorMessage + messagePartA + messagePartC);
                complaintService.deleteComplaint(complaint);
            }
            return finalize(complaintLoggingRequest, complaints, messagePartA, messagePartC);
        }
    }

    private boolean updateComplaints(boolean processorStatus, List<Complaint> complaints) throws Exception{
        if(processorStatus) {
            saveComplaintFilter.process(complaints);
            return true;
        }
        return false;
    }

    private List<Complaint> finalize(ComplaintLoggingRequest complaintLoggingRequest, List<Complaint> complaints, String messagePartA, String messagePartB){
        complaintLoggingRequest.setComplaints(complaints);
        logger.info(messagePartA + complaints.size() + messagePartB);
        return complaintLoggingRequest.getComplaints();
    }


    @Resource(name = "complaintRequestFilterProcessor")
    public void setComplaintRequestFilterProcessors(List<ComplaintRequestFilterProcessor> complaintRequestFilterProcessors) {
        this.complaintRequestFilterProcessors = complaintRequestFilterProcessors;
    }

    @Autowired
    public void setSaveComplaintFilter(SaveComplaintFilter saveComplaintFilter) {
        this.saveComplaintFilter = saveComplaintFilter;
    }

    @Autowired
    public void setComplaintService(ComplaintServiceImpl complaintService) {
        this.complaintService = complaintService;
    }
}
