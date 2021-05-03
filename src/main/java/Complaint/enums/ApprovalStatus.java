package Complaint.enums;

public enum ApprovalStatus {

    APPROVED(1),
    DECLINED(0);

    private int approvalStatus;

    ApprovalStatus(int approvalStatus) {
        this.approvalStatus = approvalStatus;
    }

    public int getApprovalStatus() {
        return approvalStatus;
    }

    public void setApprovalStatus(int approvalStatus) {
        this.approvalStatus = approvalStatus;
    }
}
