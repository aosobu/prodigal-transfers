package Complaint.service;

import Complaint.enums.ClientType;
import Complaint.enums.SchemeType;
import Complaint.model.Customer;
import Complaint.repository.dao.FinacleDao;
import Complaint.repository.dao.InfoPoolDao;
import com.teamapt.exceptions.CosmosServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomerService {

    private InfoPoolDao infoPoolDao;
    private FinacleDao finacleDao;

    public Customer getCustomerDetails(String accountNumber) throws CosmosServiceException {
        try {
            Customer customer = finacleDao.getCustomerFromGam(accountNumber);
            Customer customerInfopool = infoPoolDao.getCustomerWithCustomerId(customer.getCustomerId());

            customer.setAccountType(getAccountSchemeTypeDescription(customer.getSchemeType()));
            customer.setAccountNumber(accountNumber);
            customer.setEmail(customerInfopool.getEmail());
            customer.setPhone(customerInfopool.getPhone());
            customer.setClientType(customerInfopool.getClientType().contains("corp") ?
                    ClientType.CORPORATE.getCode() : ClientType.INDIVIDUAL.getCode());
            customer.setAddressLine1(customerInfopool.getAddressLine1());
            return customer;
        } catch (Exception e) {
            throw new CosmosServiceException("Error retrieving customer details {} " + e.getMessage());
        }
    }

    private String getAccountSchemeTypeDescription(String schemeType) {
        switch (schemeType) {
            case "SBA":
                return SchemeType.SAVINGS.getSchemeType();
            case "CAA":
                return SchemeType.CURRENT.getSchemeType();
            case "ODA":
                return SchemeType.OVERDRAFT.getSchemeType();
            case "LAA":
                return SchemeType.LOAN.getSchemeType();
            case "CLA":
                return SchemeType.LOAN.getSchemeType();
            default:
                return SchemeType.OTHER.getSchemeType();
        }
    }

    @Autowired
    public void setInfoPoolDao(InfoPoolDao infoPoolDao) {
        this.infoPoolDao = infoPoolDao;
    }

    @Autowired
    public void setFinacleDao(FinacleDao finacleDao) {
        this.finacleDao = finacleDao;
    }
}
