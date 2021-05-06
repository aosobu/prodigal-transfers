package Complaint.Service.complaintrequestprocessor;

import Complaint.model.Complaint;
import org.apache.commons.lang3.RandomStringUtils;

public class TrackingNumberFilter implements ComplaintRequestFilterProcessor {

    @Override
    public Complaint processTwo(Complaint complaint) throws Exception {

        try {

            int length = 4;
            String generatedLetters = RandomStringUtils.random(length, true, false);
            String generatedNumbers = RandomStringUtils.random(length, false, true);

            String trackingNumber = generatedNumbers + generatedLetters.toUpperCase();

            complaint.setTrackingNumber(trackingNumber);
        }
        catch (Exception e) {
            throw new Exception("Wrong attempt");
        }

        return complaint;
    }
}
