package Complaint.enums;

public enum SchemeType {

    SAVINGS("SAVINGS"),
    CURRENT("CURRENT"),
    OVERDRAFT("OVERDRAFT"),
    LOAN("CURRENT"),
    OTHER("OTHER");

    private String schemeType;

    SchemeType(String schemeType) {
        this.schemeType= schemeType;
    }

    public String getSchemeType() {
        return schemeType;
    }

    public void setSchemeType(String schemeType) {
        this.schemeType = schemeType;
    }
}
