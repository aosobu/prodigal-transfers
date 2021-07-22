package Complaint.service.complaintrequestprocessor;

import Complaint.model.Complaint;
import Complaint.model.ComplaintInstruction;
import Complaint.repository.CustomerInstructionRepository;
import Complaint.utilities.DatesUtils;
import com.teamapt.exceptions.CosmosServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

@Service
public class SaveComplaintInstructionFilter implements ComplaintRequestFilterProcessor {

    private CustomerInstructionRepository customerInstructionRepository;
    private String complaintFilesPath;

    @Override
    public String getComplaintRequestFilterProcessorName() {
        return SaveComplaintInstructionFilter.class.getName();
    }

    @Override
    public boolean isApplicable(Complaint complaint) {
        String filePath = complaint.getComplaintInstruction().getFilePath();
        return filePath == null || filePath.isEmpty();
    }

    @Override
    public Complaint process(Complaint complaint, List<Map<String, MultipartFile>> instruction) throws Exception {
        Map<String, MultipartFile> instructionFileMap = instruction.get(0);

        try {
            String fileName = getKeyFromInstructionMap(instructionFileMap);
            byte[] bytes = instructionFileMap.get(fileName).getBytes();
            if (bytes.length == 0)
                return complaint;

            String filePath = complaintFilesPath + "/"
                            + complaint.getComplaintCustomer().getCustomerAccountName().replace(" ", "") + "_"
                            + complaint.getId() + "_"
                            + DatesUtils.getInstantDateTimeInDifferentFormat()
                            + ".pdf";

            File newFile = new File(filePath);

            if (!newFile.exists()) {
                newFile.getParentFile().mkdirs();
                newFile.createNewFile();
            }

            OutputStream outputStream = new FileOutputStream(newFile);
            outputStream.write(bytes);
            outputStream.flush();
            outputStream.close();

            ComplaintInstruction complaintInstruction = complaint.getComplaintInstruction();
            complaintInstruction.setName(fileName);
            complaintInstruction.setFilePath(filePath);

            complaint.setComplaintInstruction(complaintInstruction);

        } catch (Exception e) {
            throw new CosmosServiceException("Error saving reports: " + e.getMessage());
        }

        return complaint;
    }

    private String getKeyFromInstructionMap(Map<String, MultipartFile> instructionFileMap){
        String fileName  = "";
        for (Map.Entry<String, MultipartFile> pair : instructionFileMap.entrySet()) {
            fileName = pair.getKey();
        }
        return fileName;
    }

    @Autowired
    public void setCustomerInstructionRepository(CustomerInstructionRepository customerInstructionRepository) {
        this.customerInstructionRepository = customerInstructionRepository;
    }

    @Autowired
    public void setComplaintFilesPath(@Value("${complaint.file.path}") String complaintFilesPath) {
        this.complaintFilesPath = complaintFilesPath;
    }
}
