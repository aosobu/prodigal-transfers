package Complaint.service.complaintrequestprocessor;

import Complaint.model.Complaint;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

public interface ComplaintRequestFilterProcessor {

    String getComplaintRequestFilterProcessorName();
    boolean isApplicable(Complaint complaint);
    Complaint process(Complaint complaint, List<Map<String, MultipartFile>> fileMap) throws Exception;
}
