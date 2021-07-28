package Complaint.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@AllArgsConstructor
public enum Role {
    ADMIN("ADMIN"),
    INPUTTER("INPUTTER"),
    VIEWER("VIEWER");

    private String role;
}
