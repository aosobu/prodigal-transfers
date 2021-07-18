package Complaint.enums;

public enum ClientType {

    INDIVIDUAL("INDV"),
    CORPORATE("CORP");

    private String code;

    ClientType(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
