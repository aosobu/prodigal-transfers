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
    public Object saveComplaint(ProceedingJoinPoint pjp) throws Throwable {
        Object[] arguments = pjp.getArgs();

        for(int i = 0; i < arguments.length; i++){
            if(arguments[i] instanceof ComplaintLoggingRequest){
                pjp.proceed(saveComplaints((ComplaintLoggingRequest) arguments[i]));
            }
        }
        return  null;
    }

    private Object[] saveComplaints(ComplaintLoggingRequest complaintLoggingRequest){
        List<Complaint> savedComplaints;
        Object [] args = new Object[1];

        try {
            savedComplaints =  saveComplaintFilter.process(complaintLoggingRequest.getComplaints());
            complaintLoggingRequest.setComplaints(savedComplaints);
            args[0] = complaintLoggingRequest;

        } catch (Exception e) {
            logger.info("Error saving complaints from save aspect {} " + e.getMessage());
        }
        return args;
    }

    @Autowired
    public void setSaveComplaintFilter(SaveComplaintFilter saveComplaintFilter) {
        this.saveComplaintFilter = saveComplaintFilter;
    }
}
