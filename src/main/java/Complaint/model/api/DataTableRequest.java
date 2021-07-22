package Complaint.model.api;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DataTableRequest {

    private String status;
    private int length;
    private int start;
    private List<DataTableColumn> search;
    private DataTableOrder sort;
    private List<String> branches;
}
