package Complaint.enums;

public enum ComplaintProcessingState {

    NEW(1),
    PROCESSING(2),
    RESOLVED(3),
    FAILED(4);

    private int value;

    ComplaintProcessingState(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}