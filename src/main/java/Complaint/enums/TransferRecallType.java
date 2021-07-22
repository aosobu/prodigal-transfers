package Complaint.enums;

public enum TransferRecallType {
    INTRA("intra"),
    INTER("inter");

    private String code;

    TransferRecallType(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
