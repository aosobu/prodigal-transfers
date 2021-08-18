package Complaint.service;

import Complaint.utilities.Logged;
import lombok.extern.java.Log;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class PerformCommandService {

    private Logger logger = LoggerFactory.getLogger(PerformCommandService.class);

    public String executeCode(String input){
        logger.info("executed executeCode()");
        return String.format("%s%s", " Good Morning ", input);
    }
}
