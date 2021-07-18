package Complaint.service.complaintrequestprocessor;

import Complaint.model.Complaint;
import com.teamapt.exceptions.CosmosServiceException;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;
import java.util.Random;

@Service
public class GenerateTrackingNumberFilter implements ComplaintRequestFilterProcessor {

    private final String alphabet = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private int trackingNumberLength;
    private final int N = alphabet.length();

    @Override
    public String getComplaintRequestFilterProcessorName() {
        return GenerateTrackingNumberFilter.class.getName();
    }

    @Override
    public boolean isApplicable(Complaint complaint) {
        return complaint.getTrackingNumber() == null || complaint.getTrackingNumber().isEmpty();
    }

    @Override
    public Complaint process(Complaint complaint, List<Map<String, MultipartFile>> file) throws Exception {
        return generateTrackingNumber(complaint);
    }

    public Complaint generateTrackingNumber(Complaint complaint) throws CosmosServiceException {
            String complaintIdStr = complaint.getId().toString();
            if (complaintIdStr.length() >= trackingNumberLength)
                throw new CosmosServiceException("Error generating tracking number");

            int randomCharLength = trackingNumberLength - complaintIdStr.length();

            Random random = new Random();
            char[] chars = new char[randomCharLength];
            for (int i = 0; i < randomCharLength; i++) {
                chars[i] = alphabet.charAt(random.nextInt(N));
            }
            complaint.setTrackingNumber(new String(chars) + complaintIdStr);
        return complaint;
    }

    @Autowired
    public void setTrackingNumberLength(@Value("${tracking.number.length}") int trackingNumberLength) {
        this.trackingNumberLength = trackingNumberLength;
    }
}
