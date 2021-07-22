package Complaint.service.aspects;

import Complaint.model.Complaint;
import Complaint.model.api.ComplaintLoggingRequest;
import Complaint.service.ComplaintServiceImpl;
import Complaint.service.complaintrequestprocessor.SaveComplaintFilter;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Aspect
@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class SaveComplaint {

    private Logger logger = LoggerFactory.getLogger(SaveComplaint.class);

    private SaveComplaintFilter saveComplaintFilter;

    @Around(value = "@annotation(Complaint.utilities.SaveComplaint)")
    public List<Complaint> saveComplaint(ProceedingJoinPoint pjp) throws Throwable {

        Object complaintLoggingRequest = null;
        List<Complaint> complaint = new ArrayList<>();
        ComplaintLoggingRequest complaintLoggingRequestClone = new ComplaintLoggingRequest();

        Object[] arguments = pjp.getArgs();

        for(int i = 0; i < arguments.length; i++){
            if(arguments[i] instanceof ComplaintLoggingRequest){
                complaintLoggingRequest = pjp.proceed(saveComplaints((ComplaintLoggingRequest) arguments[i]));
                List<Complaint> lister =  ((ArrayList) complaintLoggingRequest);
                complaintLoggingRequestClone.setComplaints(lister);
                arguments = saveComplaints(complaintLoggingRequestClone);
                logger.info("Complaint Successfully Saved {} ");
                //TODO: if complaint is not succesfully saved with tracking number,
                //TODO: delete entire complaint object and inform user of error
            }
        }

        complaint.addAll(complaintLoggingRequestClone.getComplaints());
        return complaint;
    }

    private Object[] saveComplaints(ComplaintLoggingRequest complaintLoggingRequest){
        List<Complaint> savedComplaints;
        Object [] args = new Object[1];

        try {
            savedComplaints =  saveComplaintFilter.process(complaintLoggingRequest.getComplaints());
            complaintLoggingRequest.setComplaints(savedComplaints);
            args[0] = complaintLoggingRequest;

        } catch (Exception e) {
            logger.info("Error Saving Complaints {} " + e.getMessage());
        }
        return args;
    }

    @Autowired
    public void setSaveComplaintFilter(SaveComplaintFilter saveComplaintFilter) {
        this.saveComplaintFilter = saveComplaintFilter;
    }
}
