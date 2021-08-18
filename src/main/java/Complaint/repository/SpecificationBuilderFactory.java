package Complaint.repository;

import Complaint.enums.TransferRecallType;
import Complaint.model.Complaint;
import Complaint.model.api.DataTableRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.domain.Specifications;

import java.util.List;

public class SpecificationBuilderFactory {

    public static Specifications<Complaint> getBaseSpecification(DataTableRequest request){
        switch(request.getProcessingState().toString()){
            case "0" : if(request.getRecallType().equalsIgnoreCase(TransferRecallType.INTER.getCode())){
                            return Specifications.where(ComplaintSpecsBranch.base(0l, TransferRecallType.INTER.getCode()));
                        }
                        else if(request.getRecallType().equalsIgnoreCase(TransferRecallType.INTRA.getCode())){
                            return Specifications.where(ComplaintSpecsBranch.base(0l, TransferRecallType.INTRA.getCode()));
                        }
            case "1" : return Specifications.where(ComplaintSpecsBranch.base(1l, null));
            case "2" : return Specifications.where(ComplaintSpecsBranch.base(2l, null));
            case "3" : return Specifications.where(ComplaintSpecsBranch.base(3l, null));
            default:  return Specifications.where(ComplaintSpecsBranch.base(null, null));
        }
    }

    public static Specifications<Complaint> getSpecificationForBranchLoggedComplaints(DataTableRequest request, String staffId){
        List<String> branches = request.getBranches();
        Specifications<Complaint> specification = null;
        if(!branches.isEmpty()){
            return Specifications.where(ComplaintSpecsBranch.branchLoggedBase(branches, null));
        }
        if(staffId != null){
            specification =  Specifications.where(ComplaintSpecsBranch.branchLoggedBase(null, staffId));
        }
        return specification;
    }

}
