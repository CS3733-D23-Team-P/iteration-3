package edu.wpi.punchy_pegasi.generated;

import edu.wpi.punchy_pegasi.backend.PdbController;
import edu.wpi.punchy_pegasi.schema.ConferenceRoomEntry;

import edu.wpi.punchy_pegasi.schema.RequestEntry;
import edu.wpi.punchy_pegasi.schema.TableType;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.sql.ResultSet;

import static org.junit.jupiter.api.Assertions.*;

import static org.junit.jupiter.api.Assertions.*;
@Slf4j
class ConferenceRoomEntryDaoImplTest {
    static PdbController pdbController;
    static String[] fields;

    static ConferenceRoomEntryDaoImpl dao;


    @BeforeAll
    static void init(){
        fields = new String[]{"serviceID", "roomNumber", "staffAssignment", "additionalNotes", "status", "beginningTime", "endTime"};
        pdbController = new PdbController("jdbc:postgresql://database.cs.wpi.edu:5432/teampdb", "teamp", "teamp130");
        dao = new ConferenceRoomEntryDaoImpl(pdbController);
        try {
            pdbController.initTableByType(TableType.CONFERENCEREQUESTS);
        } catch (PdbController.DatabaseException e) {
            throw new RuntimeException(e);
        }
    }
    @Test
    void get() {
        ConferenceRoomEntry room = new ConferenceRoomEntry(UUID.randomUUID(), "testRoom", "testStaff", "testNotes", RequestEntry.Status.PROCESSING, "testBeginning", "testEnd");

        Object[] values = new Object[]{room.getServiceID(), room.getRoomNumber(), room.getStaffAssignment(), room.getAdditionalNotes(), room.getStatus(), room.getBeginningTime(), room.getEndTime()};
        try{
            pdbController.insertQuery(TableType.CONFERENCEREQUESTS, fields, values);
        } catch (PdbController.DatabaseException e){
            throw new RuntimeException(e);
        }
        Optional<ConferenceRoomEntry> results = dao.get(room.getServiceID());
        ConferenceRoomEntry daoresult = results.get();
        assertEquals(daoresult, room);
        try{
            pdbController.deleteQuery(TableType.CONFERENCEREQUESTS, "serviceID", room.getServiceID());
        } catch(PdbController.DatabaseException e){
            throw new RuntimeException(e);
        }
    }

    @Test
    void testGet() {
    }

    @Test
    void getAll() {
        var values0 = new Object[] {
                UUID.randomUUID(),
                "testRoomNum0",
                "testStaff0",
                "testNotes0",
                RequestEntry.Status.PROCESSING,
                "beginTime0",
                "endTime0"
        };
        var values1 = new Object[] {
                UUID.randomUUID(),
                "testRoomNum1",
                "testStaff1",
                "testNotes1",
                RequestEntry.Status.PROCESSING,
                "beginTime1",
                "endTime1"
        };
        var values2 = new Object[] {
                UUID.randomUUID(),
                "testRoomNum2",
                "testStaff2",
                "testNotes2",
                RequestEntry.Status.PROCESSING,
                "beginTime2",
                "endTime2"
        };

        var valuesSet = new Object[][] {values0, values1, values2};
        var refMap = new HashMap<UUID, ConferenceRoomEntry>();

        for(var values : valuesSet) {
            try {
                pdbController.insertQuery(TableType.CONFERENCEREQUESTS, fields, values);
            } catch (PdbController.DatabaseException e) {
                assert false : e.getMessage();
            }
            var uuid = (UUID) values[0];
            var roomNum = (String) values[1];
            var staff = (String) values[2];
            var notes = (String) values[3];
            var status = (RequestEntry.Status) values[4];
            var beginTime = (String) values[5];
            var endTime = (String) values[6];
            refMap.put(uuid, new ConferenceRoomEntry(uuid, roomNum, staff, notes, status, beginTime, endTime));
        }

        Map<UUID, ConferenceRoomEntry> resultMap = dao.getAll();
        for (var entry : resultMap.entrySet()) {
            try {
                pdbController.deleteQuery(TableType.CONFERENCEREQUESTS, "serviceID", entry.getKey());
            } catch (PdbController.DatabaseException e) {
                assert false : "Failed to delete from database";
            }
        }
        assertEquals(refMap, resultMap);

    }

    @Test
    void save() {
        var dao = new ConferenceRoomEntryDaoImpl(pdbController);
        UUID uuid = UUID.randomUUID();
        ConferenceRoomEntry conference = new ConferenceRoomEntry(uuid, "testRoom", "testStaff", "testNotes", RequestEntry.Status.PROCESSING, "testBeginning", "testEnd");
        dao.save(conference);
        Optional<ConferenceRoomEntry> results = dao.get(uuid);
        ConferenceRoomEntry daoresult = results.get();
        assertEquals(conference, daoresult);
        try {
            pdbController.deleteQuery(TableType.CONFERENCEREQUESTS, "serviceID", uuid);
        } catch (PdbController.DatabaseException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void update() {
        UUID uuid = UUID.randomUUID();
        ConferenceRoomEntry conference = new ConferenceRoomEntry(uuid, "testRoom", "testStaff", "testNotes", RequestEntry.Status.PROCESSING, "testBeginning", "testEnd");
        dao.save(conference);

        ConferenceRoomEntry updatedConference = new ConferenceRoomEntry(uuid, "updatedTestRoom", "testStaff", "testNotes", RequestEntry.Status.DONE, "testBeginning", "testEnd");
        ConferenceRoomEntry.Field[] fields = {ConferenceRoomEntry.Field.SERVICE_ID, ConferenceRoomEntry.Field.ROOM_NUMBER, ConferenceRoomEntry.Field.STATUS};
        dao.update(updatedConference, fields);

        Optional<ConferenceRoomEntry> results = dao.get(uuid);
        ConferenceRoomEntry daoresult = results.get();
        assertEquals(updatedConference, daoresult);
        try {
            pdbController.deleteQuery(TableType.CONFERENCEREQUESTS, "serviceID", uuid);
        } catch (PdbController.DatabaseException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void delete() {
        ConferenceRoomEntry conferenceRoom =
                new ConferenceRoomEntry(
                        UUID.randomUUID(),
                        "testRoomNum",
                        "testStaff",
                        "testNotes",
                        RequestEntry.Status.PROCESSING,
                        "beginTime",
                        "endTime");

        var values = new Object[] {
                conferenceRoom.getServiceID(),
                conferenceRoom.getRoomNumber(),
                conferenceRoom.getStaffAssignment(),
                conferenceRoom.getAdditionalNotes(),
                conferenceRoom.getStatus(),
                conferenceRoom.getBeginningTime(),
                conferenceRoom.getEndTime()
        };

        try {
            pdbController.insertQuery(TableType.CONFERENCEREQUESTS, fields, values);
        } catch (PdbController.DatabaseException e) {
            assert false: "Failed to insert into database";
            log.error("Failed to insert into database", e);
        }

        try {
            ResultSet rs = pdbController.searchQuery(TableType.CONFERENCEREQUESTS, "serviceID", conferenceRoom.getServiceID());
        } catch (PdbController.DatabaseException e) {
            assert false: "Failed to find the entry in the database";
            log.error("Failed to find the entry in the database", e);
        }

        dao.delete(conferenceRoom);

        try {
            ResultSet rs = pdbController.searchQuery(TableType.CONFERENCEREQUESTS, "serviceID", conferenceRoom.getServiceID());
        } catch (PdbController.DatabaseException e) {
            assert true: "Successfully deleted the entry from the database";
            assertTrue(e.getMessage().contains("SQL error"));
        }
    }
}