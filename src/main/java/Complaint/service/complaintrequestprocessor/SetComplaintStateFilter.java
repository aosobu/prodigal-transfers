package Complaint.service.complaintrequestprocessor;

import Complaint.enums.ComplaintProcessingState;
import Complaint.enums.TransferRecallType;
import Complaint.model.Complaint;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

@Service
public class SetComplaintStateFilter implements ComplaintRequestFilterProcessor{

    @Override
    public Complaint process(Complaint complaint, List<Map<String, MultipartFile>> instruction) throws Exception {
        complaint.getComplaintState().setProcessingState(ComplaintProcessingState.NEW.getValue());
        return complaint;
    }

    @Override
    public String getComplaintRequestFilterProcessorName() {
        return SetComplaintStateFilter.class.getName();
    }

    @Override
    public boolean isApplicable(Complaint complaint) {
        return complaint.getComplaintState().getProcessingState() <= 0;
    }

}
