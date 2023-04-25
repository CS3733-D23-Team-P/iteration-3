package edu.wpi.punchy_pegasi.generator.schema;

import edu.wpi.punchy_pegasi.generator.SchemaID;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Alert {
    @SchemaID
    private Long uuid;
    private String alertTitle;
    private String description;
    private ReadStatus readStatus;

    public enum ReadStatus {
        READ,
        UNREAD
    }
}
