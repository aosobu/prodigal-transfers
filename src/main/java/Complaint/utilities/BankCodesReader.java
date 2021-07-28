package Complaint.utilities;

import Complaint.model.Bank;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class BankCodesReader {

    private Logger logger = LoggerFactory.getLogger(BankCodesReader.class);

    public List<Bank> getBanks(){
        //String path = "/Users/aosobu/Documents/ProjectTeamApt/fidelity/transfer-recall/config/bank_codes.csv"; //config/bank_codes.csv"
        String path = "config/bank_codes.csv";
        List<Bank> banks = new ArrayList<>();

        BufferedReader fileReader = null;

        try {
            int count = 0;
            fileReader = new BufferedReader(new FileReader(path));
            String line;
            while ((line = fileReader.readLine()) != null) {
                String[] tokens = line.split(",");
                if (tokens.length != 3) {
                    continue;
                }
                String bankAbbreviation = tokens[0].trim();
                String bankName = tokens[1].trim();
                String bankCode = tokens[2].trim();
                Bank bank = new Bank(bankAbbreviation, bankCode, bankName);
                banks.add(bank);
                count++;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (fileReader != null) {
                    fileReader.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return banks;
    }
}
