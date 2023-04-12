package edu.wpi.punchy_pegasi.schema;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
public class Account {
    
    private String username;
    private String password;
    private Long employeeID;
    private AccountType accountType;

        public enum AccountType {
            NONE,
            ADMIN,
            STAFF;
        }

    @lombok.RequiredArgsConstructor
    public enum Field {
        USERNAME("username"),
        PASSWORD("password"),
        EMPLOYEE_ID("employeeID"),
        ACCOUNT_TYPE("accountType");
        @lombok.Getter
        private final String colName;
        public Object getValue(edu.wpi.punchy_pegasi.schema.Account ref){
            return ref.getFromField(this);
        }
    }
    public Object getFromField(Field field) {
        return switch (field) {
            case USERNAME -> getUsername();
            case PASSWORD -> getPassword();
            case EMPLOYEE_ID -> getEmployeeID();
            case ACCOUNT_TYPE -> getAccountType();
        };
    }

}