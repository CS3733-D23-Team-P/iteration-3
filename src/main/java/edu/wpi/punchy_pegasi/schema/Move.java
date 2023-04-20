package edu.wpi.punchy_pegasi.schema;


import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class Move {
    
    private Long uuid;
    private Long nodeID;
    private String longName;
    private LocalDate date;
@lombok.RequiredArgsConstructor
public enum Field implements IField<edu.wpi.punchy_pegasi.schema.Move>{
        UUID("uuid"),
        NODE_ID("nodeID"),
        LONG_NAME("longName"),
        DATE("date");
        @lombok.Getter
        private final String colName;
        public Object getValue(edu.wpi.punchy_pegasi.schema.Move ref){
            return ref.getFromField(this);
        }
    }
    public Object getFromField(Field field) {
        return switch (field) {
            case UUID -> getUuid();
            case NODE_ID -> getNodeID();
            case LONG_NAME -> getLongName();
            case DATE -> getDate();
        };
    }

}