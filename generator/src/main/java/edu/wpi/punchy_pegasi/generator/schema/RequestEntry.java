package edu.wpi.punchy_pegasi.generator.schema;

import edu.wpi.punchy_pegasi.generator.SchemaID;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor
public class RequestEntry {
    @SchemaID
    protected final UUID serviceID;
    protected final Long locationName;
    protected final Long staffAssignment;
    protected final String additionalNotes;
    protected final Status status;
    protected final Long employeeID;

    public enum Status {
        NONE,
        PROCESSING,
        DONE
    }
}
