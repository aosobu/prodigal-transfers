package Complaint.Service.complaintrequestprocessor;

import Complaint.model.Complaint;
import org.apache.commons.lang3.RandomStringUtils;

import java.util.List;

public class TrackingNumberFilter implements ComplaintRequestFilterProcessor {

    @Override
    public List<Complaint> process(List<Complaint> complaint) throws Exception {

        for (Complaint aComplaint : complaint) {
            try {

                int length = 4;
                String generatedLetters = RandomStringUtils.random(length, true, false);
                String generatedNumbers = RandomStringUtils.random(length, false, true);

                String trackingNumber = generatedNumbers + generatedLetters.toUpperCase();

                aComplaint.setTrackingNumber(trackingNumber);
            }
            catch (Exception e) {
                throw new Exception("Wrong attempt");
            }
        }

        return complaint;
    }
}
