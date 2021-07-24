package Complaint.service.interfaces;

import Complaint.model.Bank;

import java.util.List;

public interface BankService {
    List<Bank> getAllBanks();
    List<Bank> saveAllBanks(List<Bank> bankList);
}
