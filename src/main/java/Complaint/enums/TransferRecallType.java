package Complaint.enums;

public enum TransferRecallType {
    INTRA("inter"),
    INTER("intra");

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
