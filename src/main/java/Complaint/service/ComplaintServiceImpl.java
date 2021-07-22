package Complaint.service;

import Complaint.model.Complaint;
import Complaint.model.api.DataTableColumn;
import Complaint.model.api.DataTableOrder;
import Complaint.model.api.DataTableRequest;
import Complaint.repository.ComplaintRepository;
import Complaint.repository.ComplaintSpecsBranch;
import Complaint.service.interfaces.ComplaintService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ComplaintServiceImpl implements ComplaintService {

    private ComplaintRepository complaintRepository;

    @Override
    @Transactional
    public Complaint saveComplaint(Complaint complaint) {
       return complaintRepository.save(complaint);
    }

    @Override
    public Long getComplaintsByStaffIdAndProcessingStateAndRecallType(String staffId, Long processingState, String recallType) {
        return complaintRepository.countAllByBranchUserStaffIdAndComplaintStateProcessingStateAndRecallType(staffId, processingState, recallType);
    }

    @Autowired
    public void setComplaintRepository(ComplaintRepository complaintRepository) {
        this.complaintRepository = complaintRepository;
    }

    public Map<String, Object> getBranchLoggedComplaintHistory(DataTableRequest request, String staffId) {
        Specifications<Complaint> baseSearchSpecs = Specifications.where(ComplaintSpecsBranch.base(null, request.getBranches()));

        int page = request.getStart() / request.getLength();
        Specifications<Complaint> searchSpecs = getComplaintSpecs(request, baseSearchSpecs);
        Sort sort = getSortSpecs(request);
        Page<Complaint> requests = complaintRepository.findAll(searchSpecs, new CustomPageRequest(page, request.getLength(), sort));
        Map<String, Object> summary = new HashMap<>();
        summary.put("totalPages", requests.getTotalPages());
        summary.put("data", requests.getContent());

        return summary;
    }

    private Sort getSortSpecs(DataTableRequest request) {
        DataTableOrder sortOrder = request.getSort();
        List<Sort.Order> orderList = new ArrayList<>();
        Sort.Direction direction = Sort.Direction.DESC;
        String predicate = "createdTime";
        if (sortOrder != null) {
            predicate = sortOrder.getPredicate();
            if (sortOrder.isReverse()) {
                direction = Sort.Direction.DESC;
            } else {
                direction = Sort.Direction.ASC;
            }
        }
        orderList.add(new Sort.Order(direction, predicate));
        Sort sort = new CustomSort(orderList);
        return sort;
    }

    private Specifications<Complaint> getComplaintSpecs(DataTableRequest request, Specifications<Complaint> searchSpecs) {

        List<DataTableColumn> columns = request.getSearch();
        if (columns == null) {
            return searchSpecs;
        }
        for (DataTableColumn column : columns) {
            String columnName = column.getColumnName();
            String keyword = column.getValue();
            if (StringUtils.hasText(keyword)) {
                switch (columnName) {

                }
            }
        }
        return searchSpecs;
    }

    private class CustomPageRequest extends PageRequest{
        protected CustomPageRequest(int page, int size, Sort sort) {
            super(page, size, sort);
        }
    }

    private class CustomSort extends Sort{
        protected CustomSort(List<Order> orders) {
            super(orders);
        }
    }
}
