package Complaint.config;

import Complaint.service.complaintrequestprocessor.ComplaintRequestFilterProcessor;
import org.intellij.lang.annotations.RegExp;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Configuration
public class ServiceBooststrapFactory {

    private Logger logger = LoggerFactory.getLogger(ServiceBooststrapFactory.class);

    @Bean(name = "complaintRequestFilterProcessor")
    public List<ComplaintRequestFilterProcessor> complaintRequestFilterProcessorList(List<ComplaintRequestFilterProcessor> requests){
        List<ComplaintRequestFilterProcessor> requestList = new ArrayList<>();
        requestList.addAll(requests);
        logger.info("Complaint Request Filter Processors {} " + requestList.size());
        return requestList;
    }
}
