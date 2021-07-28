package Complaint.model.api;

import Complaint.model.BranchUser;
import Complaint.model.Complaint;
import Complaint.service.BranchUserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.teamapt.exceptions.ApiException;
import com.teamapt.exceptions.CosmosServiceException;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.assertj.core.util.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Service
public class ComplaintLoggingRequest {

    private static Logger logger = LoggerFactory.getLogger(ComplaintLoggingRequest.class);

    private List<Complaint> complaints = new ArrayList<>();
    private List<Map<String, MultipartFile>> multipartFiles = new ArrayList<>();
    private static Map<String, MultipartFile> files = new HashMap<>();
    private String initiator;

    private static BranchUserService branchUserService;

    public static ComplaintLoggingRequest with(String complaint, MultipartFile file, String fileName, String initiator) throws Exception {
        ComplaintLoggingRequest request= new ComplaintLoggingRequest();
        initiator = "P1222"; // hard coded for test

        request.setComplaints(Collections.singletonList(getComplaintObjects(complaint)));
        request.getComplaints().get(0).getBranchUser().setStaffId(initiator);
        process(request.getComplaints().get(0));
        if(!file.isEmpty() && !fileName.isEmpty()){
            setFile(file, fileName);
            request.setMultipartFiles(Collections.singletonList(files));
        }else{
            throw new ApiException("Customer Instruction is not set {} ");
        }
        return request;
    }

    private static Complaint getComplaintObjects(String jsonComplaint) throws ApiException {
        ObjectMapper mapper = new ObjectMapper();
        Complaint complaint = new Complaint();
        try {
            complaint = mapper.readValue(jsonComplaint, Complaint.class);
        } catch (JsonProcessingException e) {
            throw new ApiException("Log complaint failed due to error encountered parsing complaint object {} ");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return complaint;
    }

    public static Complaint process(Complaint complaint) throws Exception {
        BranchUser branchUser = new BranchUser();

        branchUser = branchUserService.getUserDetails(complaint.getBranchUser().getStaffId());
        if(branchUser == null){
            throw new ApiException("Could not save complaint as branch user does not exist {} ");
        }

        complaint.getBranchUser().setStaffId(branchUser.getStaffId());
        complaint.getBranchUser().setBranchCode(branchUser.getBranchCode());
        complaint.getBranchUser().setStaffName(branchUser.getStaffName());

        return complaint;
    }

    private static void setFile (MultipartFile file, String fileName) throws ApiException {
        try{
            files.put(fileName, file);
        }catch(Exception e) {
            throw new ApiException("Log complaint failed because Customer Instruction is not set {} ");
        }
    }

    @Autowired
    public void setBranchUserService(BranchUserService branchUserService) {
        this.branchUserService = branchUserService;
    }
}


