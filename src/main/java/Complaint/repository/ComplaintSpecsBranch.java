package Complaint.repository;

import javax.persistence.criteria.*;
import java.util.List;

import Complaint.enums.ComplaintProcessingState;
import Complaint.enums.Role;
import Complaint.enums.TransferRecallType;
import Complaint.model.*;
import org.springframework.data.jpa.domain.Specification;

public class ComplaintSpecsBranch {

    public static Specification<Complaint> base(Long processingState, String recallType) {
        return new Specification<Complaint>() {
            public Predicate toPredicate(Root<Complaint> root, CriteriaQuery<?> query,
                                         CriteriaBuilder builder) {
                Predicate predicate = null;

                if(processingState == 0l){
                    if(recallType.equalsIgnoreCase(TransferRecallType.INTER.getCode())){
                        return builder.and(
                                builder.equal(root.get(Complaint_.recallType), TransferRecallType.INTER.getCode()),
                                builder.equal(root.join(Complaint_.complaintState).get(ComplaintState_.processingState), processingState)
                        );
                    }else if(recallType.equalsIgnoreCase(TransferRecallType.INTRA.getCode())){
                        return builder.and(
                                builder.equal(root.get(Complaint_.recallType), TransferRecallType.INTRA.getCode()),
                                builder.equal(root.join(Complaint_.complaintState).get(ComplaintState_.processingState), processingState)
                        );
                    }
                }

                if(processingState == 1l || processingState == 2l){
                    predicate = builder.equal(root.join(Complaint_.complaintState).get(ComplaintState_.processingState), processingState);
                }

                return predicate;
            }
        };
    }

    public static Specification<Complaint> branchLoggedBase(List<String> branches, String staffId) {
        return new Specification<Complaint>() {
            public Predicate toPredicate(Root<Complaint> root, CriteriaQuery<?> query,
                                         CriteriaBuilder builder) {
                Predicate predicate = null;

                if(branches != null){
                    if (branches.size() > 0) {
                       predicate = builder.equal(root.get(Complaint_.branchCodeLogged), branches);
                    }
                }

                if(staffId != null){
                   predicate = builder.equal(root.join(Complaint_.branchUser).get(BranchUser_.staffId), staffId);
                }

                return predicate;
            }
        };
    }
}
