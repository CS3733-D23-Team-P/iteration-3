package edu.wpi.punchy_pegasi.schema;


import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Alert {
    
    private Long uuid;
    private String alertTitle;
    private String description;
    private Boolean readStatus;
@lombok.RequiredArgsConstructor
public enum Field implements IField<edu.wpi.punchy_pegasi.schema.Alert>{
        UUID("uuid"),
        ALERT_TITLE("alertTitle"),
        DESCRIPTION("description"),
        READ_STATUS("readStatus");
        @lombok.Getter
        private final String colName;
        public Object getValue(edu.wpi.punchy_pegasi.schema.Alert ref){
            return ref.getFromField(this);
        }
    }
    public Object getFromField(Field field) {
        return switch (field) {
            case UUID -> getUuid();
            case ALERT_TITLE -> getAlertTitle();
            case DESCRIPTION -> getDescription();
            case READ_STATUS -> getReadStatus();
        };
    }

}