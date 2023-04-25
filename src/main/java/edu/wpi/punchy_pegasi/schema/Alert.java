package edu.wpi.punchy_pegasi.schema;

import edu.wpi.punchy_pegasi.backend.SchemaID;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Alert {
    @SchemaID
    @com.jsoniter.annotation.JsonProperty("uuid")
    private Long uuid;
    @com.jsoniter.annotation.JsonProperty("alerttitle")
    private String alertTitle;
    @com.jsoniter.annotation.JsonProperty("description")
    private String description;
    @com.jsoniter.annotation.JsonProperty("readstatus")
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