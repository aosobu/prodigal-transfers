package Complaint.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
public enum ComplaintProcessingState {

    NEW(0l),
    ASSIGNED(1l),
    PROCESSING(2l),
    RESOLVED(3l),
    FAILED(4l);

    private Long value;

    public void setValue(Long value) {
        this.value = value;
    }
}