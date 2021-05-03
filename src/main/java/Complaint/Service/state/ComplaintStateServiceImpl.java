package Complaint.Service.state;

import Complaint.model.ComplaintState;
import Complaint.repository.ComplaintStateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.util.List;


@Service
@Transactional
public class ComplaintStateServiceImpl implements ComplaintStateService{

    @Autowired
    private ComplaintStateRepository complaintStateRepository;

    @Override
    public List<ComplaintState> getAllStates() {
        return complaintStateRepository.findAll();
    }

    @Override
    public ComplaintState getAState(Long complainStateId) {
        return complaintStateRepository.findById(complainStateId).orElse(null);
    }

    @Override
    public ComplaintState createOrUpdateAState(ComplaintState complaintState) {
        return complaintStateRepository.save(complaintState);
    }

    @Override
    public void deleteAState(Long complainStateId) {
        complaintStateRepository.deleteById(complainStateId);
    }
}
