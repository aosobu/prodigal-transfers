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
import org.springframework.web.multipart.MultipartFile;

import java.util.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ComplaintLoggingRequest {

    private static Logger logger = LoggerFactory.getLogger(ComplaintLoggingRequest.class);

    private List<Complaint> complaints = new ArrayList<>();
    private static BranchUserService bankService;

    private List<Map<String, MultipartFile>> multipartFiles = new ArrayList<>();
    private static Map<String, MultipartFile> files = new HashMap<>();
    private BranchUser branchUser;
    private String initiator;


    public static ComplaintLoggingRequest with(String complaint, MultipartFile file, String fileName, String username){
        ComplaintLoggingRequest request= new ComplaintLoggingRequest();

        request.setComplaints(Collections.singletonList(getComplaintObjects(complaint)));
        if(!file.isEmpty() && !fileName.isEmpty()){
            setFile(file, fileName);
            request.setMultipartFiles(Collections.singletonList(files));
        }else {
            try {
                throw new ApiException("Customer Instruction is not set {} ");
            } catch (ApiException e) {
                logger.info("Customer Instruction is not set {} " + e.getMessage());
            }
        }
        request.setBranchUser(mockBranchUser()); // for test purpose
        //request.setBranchUser(getBranchUser(username));
        return request;
    }

    private static Complaint getComplaintObjects(String jsonComplaint) {
        ObjectMapper mapper = new ObjectMapper();
        Complaint complaint = new Complaint();
        try {
            complaint = mapper.readValue(jsonComplaint, Complaint.class);
        } catch (JsonProcessingException e) {
            logger.info("Error encountered parsing complaint object {} " + e.getMessage());
        }
        return complaint;
    }

    private static void setFile (MultipartFile file, String fileName){
        try{
            files.put(fileName, file);
        }catch(Exception e) {
            logger.info("Error creating multipart file {} " + e.getMessage());
        }
    }

    private static BranchUser getBranchUser(String name){
        BranchUser branchUser = new BranchUser();
        try {
            branchUser = bankService.getUserDetails(name);
        } catch (CosmosServiceException e) {
            e.printStackTrace();
        }
        return branchUser;
    }

    private static BranchUser mockBranchUser(){
        BranchUser branchUser =  new BranchUser();
        branchUser.setBranchCode("021");
        branchUser.setStaffId("P076");
        branchUser.setStaffName("Isaac Anyam");
        return branchUser;
    }

    @Autowired
    public void setBranchUser(BranchUser branchUser) {
        this.branchUser = branchUser;
    }
}


