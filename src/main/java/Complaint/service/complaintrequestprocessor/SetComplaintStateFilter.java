package Complaint.service.complaintrequestprocessor;

import Complaint.enums.ComplaintProcessingState;
import Complaint.enums.TransferRecallType;
import Complaint.model.Complaint;
import Complaint.utilities.StringUtilities;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

@Service
public class SetComplaintStateFilter implements ComplaintRequestFilterProcessor{

    private Logger logger = LoggerFactory.getLogger(SetComplaintStateFilter.class);

    @Override
    public Complaint process(Complaint complaint, List<Map<String, MultipartFile>> instruction) throws Exception {
        if(StringUtils.isEmpty(complaint.getBranchUser().getBranchCode())){
            //TODO: delete complaint and report error to user
        }

        String branchCode = complaint.getBranchUser().getBranchCode();
        complaint.getComplaintState().setProcessingState(ComplaintProcessingState.NEW.getValue());

        logger.info("Branch User before setting complaint state " + complaint.getBranchUser().toString());

        if(branchCode.equalsIgnoreCase("000")){
            logger.info("Branch Code {} " +  branchCode);
            complaint.setBranchCodeLogged(branchCode);
            complaint.getBranchUser().setBranchCode(branchCode);
        }else{
            logger.info("Branch Code if not 000 {} " +  branchCode);
            complaint.setBranchCodeLogged(StringUtilities.removeLeadingZeros(complaint.getBranchUser().getBranchCode()));
            complaint.getBranchUser().setBranchCode(StringUtilities.removeLeadingZeros(complaint.getBranchUser().getBranchCode()));
        }
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
