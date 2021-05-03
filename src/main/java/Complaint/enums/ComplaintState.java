package Complaint.enums;

public enum ComplaintState {
    NEW(0),
    PROCESSING(1),
    RESOLVED(2);

    private int value;

    ComplaintState(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
