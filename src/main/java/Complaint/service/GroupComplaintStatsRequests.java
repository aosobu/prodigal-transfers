package Complaint.service;

import Complaint.model.Branch;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.teamapt.exceptions.ApiException;

import java.util.ArrayList;
import java.util.List;

public class GroupComplaintStatsRequests {

    public static List<Branch> getBranchObjects(String jsonBranch) throws ApiException {
        ObjectMapper mapper = new ObjectMapper();
        List<Branch> branches = new ArrayList<>();
        try {
            branches = mapper.readValue(jsonBranch, new TypeReference<List<Branch>>(){});
        } catch (JsonProcessingException e) {
            throw new ApiException("Log complaint failed due to error encountered parsing complaint object {} ");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return branches;
    }
}
