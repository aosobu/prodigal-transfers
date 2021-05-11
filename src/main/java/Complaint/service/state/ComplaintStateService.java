package Complaint.service.state;

import Complaint.model.ComplaintState;
import java.util.List;

public interface ComplaintStateService {

    List<ComplaintState> getAllStates();

    ComplaintState getAState(Long complainStateId);

    ComplaintState createOrUpdateAState(ComplaintState complaintState);

    void deleteAState(Long complainStateId);
}
