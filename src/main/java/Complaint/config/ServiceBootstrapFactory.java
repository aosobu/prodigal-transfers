package Complaint.config;

import Complaint.model.Bank;
import Complaint.service.BankServiceImpl;
import Complaint.service.complaintrequestprocessor.ComplaintRequestFilterProcessor;
import Complaint.utilities.BankCodesReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Configuration
public class ServiceBootstrapFactory {

    private Logger logger = LoggerFactory.getLogger(ServiceBootstrapFactory.class);

    private BankServiceImpl bankService;
    private BankCodesReader bankCodesReader;

    @Bean(name = "complaintRequestFilterProcessor")
    public List<ComplaintRequestFilterProcessor> complaintRequestFilterProcessorList(List<ComplaintRequestFilterProcessor> requests){
        List<ComplaintRequestFilterProcessor> requestList = new ArrayList<>();
        requestList.addAll(requests);
        logger.info("Complaint Request Filter Processors {} " + requestList.size());
        return requestList;
    }

    @PostConstruct
    public void injectBankData(){
        if(bankService.getAllBanks().isEmpty()) {
            List<Bank> bankList = bankCodesReader.getBanks();
            bankService.saveAllBanks(bankList);
        }
    }

    @Autowired
    public void setBankService(BankServiceImpl bankService) {
        this.bankService = bankService;
    }

    @Autowired
    public void setBankCodesReader(BankCodesReader bankCodesReader) {
        this.bankCodesReader = bankCodesReader;
    }


}
