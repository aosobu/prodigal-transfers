package Complaint.repository;

import javax.persistence.criteria.*;
import java.util.List;

import Complaint.model.BranchUser_;
import Complaint.model.Complaint;
import Complaint.model.Complaint_;
import org.springframework.data.jpa.domain.Specification;

public class ComplaintSpecsBranch {

    public static Specification<Complaint> base(String staffId, List<String> branches) {
        return new Specification<Complaint>() {
            public Predicate toPredicate(Root<Complaint> root, CriteriaQuery<?> query,
                                         CriteriaBuilder builder) {
                Predicate predicate = null;

                if (branches != null) {
                    if (branches.size() > 0) {
                        predicate = builder.equal(root.join(Complaint_.branchUser).get(BranchUser_.branchCode), branches);
                    }
                }

                if (staffId == null) {
                    return predicate;
                }

                return predicate;
            }
        };
    }
}
