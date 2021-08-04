package Complaint.service;

import Complaint.model.Exceptions;
import Complaint.repository.ExceptionsRepository;
import Complaint.service.interfaces.ExceptionsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ExceptionsServiceImpl implements ExceptionsService {

    private ExceptionsRepository exceptionsRepository;

    @Override
    public void saveException(Exceptions exceptions) {
        exceptionsRepository.save(exceptions);
    }

    @Autowired
    public void setExceptionsRepository(ExceptionsRepository exceptionsRepository) {
        this.exceptionsRepository = exceptionsRepository;
    }
}
