package Complaint.service;

import Complaint.model.Bank;
import Complaint.repository.BankRepository;
import Complaint.service.interfaces.BankService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BankServiceImpl implements BankService{

    private BankRepository bankRepository;

    @Override
    public List<Bank> getAllBanks() {
        return bankRepository.findAll();
    }

    @Override
    public List<Bank> saveAllBanks(List<Bank> bankList) {
        return bankRepository.saveAll(bankList);
    }

    @Override
    public Bank getBankByBankCode(String bankCode) {
        return bankRepository.findByBankCode(bankCode);
    }

    @Autowired
    public void setBankRepository(BankRepository bankRepository) {
        this.bankRepository = bankRepository;
    }
}
