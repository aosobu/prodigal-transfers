package Complaint.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ComplaintProcessingState {

    NEW(0l),
    PROCESSING(1l),
    RESOLVED(2l),
    DECLINED(3l);

    private Long value;

    public void setValue(Long value) {
        this.value = value;
    }
}