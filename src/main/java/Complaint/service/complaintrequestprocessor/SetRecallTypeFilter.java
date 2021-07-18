package Complaint.service.complaintrequestprocessor;

import Complaint.enums.TransferRecallType;
import Complaint.model.Complaint;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

@Service
public class SetRecallTypeFilter implements ComplaintRequestFilterProcessor {
    @Override
    public String getComplaintRequestFilterProcessorName() {
        return SetRecallTypeFilter.class.getName();
    }

    @Override
    public boolean isApplicable(Complaint complaint) {
        return complaint.getRecallType().toString().isEmpty() || complaint.getRecallType() == null;
    }

    @Override
    public Complaint process(Complaint complaint, List<Map<String, MultipartFile>> fileMap) throws Exception {
        if(complaint.getBeneficiaryBankCode() == complaint.getTransferringBankCode())
            complaint.setRecallType(TransferRecallType.INTRA);
        else
            complaint.setRecallType(TransferRecallType.INTER);

        return complaint;
    }
}
