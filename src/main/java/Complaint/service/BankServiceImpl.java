package Complaint.service;

import Complaint.model.Bank;
import Complaint.repository.BankRepository;
import Complaint.service.interfaces.BankService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BankServiceImpl implements BankService{

    private BankRepository bankRepository;
    private Logger logger = LoggerFactory.getLogger(BankServiceImpl.class);

    @Override
    public List<Bank> getAllBanks() {
        logger.info("Banks {} " + bankRepository.findAll());
        return bankRepository.findAll();
    }

    @Autowired
    public void setBankRepository(BankRepository bankRepository) {
        this.bankRepository = bankRepository;
    }
}
