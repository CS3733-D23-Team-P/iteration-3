package edu.wpi.punchy_pegasi.generated;

import edu.wpi.punchy_pegasi.backend.PdbController;
import edu.wpi.punchy_pegasi.schema.FlowerDeliveryRequestEntry;
import edu.wpi.punchy_pegasi.schema.FoodServiceRequestEntry;
import edu.wpi.punchy_pegasi.schema.RequestEntry;
import edu.wpi.punchy_pegasi.schema.TableType;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class FoodServiceRequestEntryDaoImplTest {
    static PdbController pdbController;
    static FoodServiceRequestEntryDaoImpl dao;
    static String[] fields;

    @BeforeAll
    static void init() {
        fields = new String[]{"serviceID", "roomNumber", "staffAssignment", "additionalNotes", "status", "foodSelection", "tempType", "additionalItems", "dietaryRestrictions", "patientName"};
        pdbController = new PdbController("jdbc:postgresql://database.cs.wpi.edu:5432/teampdb", "teamp", "teamp130");
        dao = new FoodServiceRequestEntryDaoImpl(pdbController);
        try {
            pdbController.initTableByType(TableType.FOODREQUESTS);
        } catch (PdbController.DatabaseException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void get() {
        List<String> additionalItems = new ArrayList<>();
        additionalItems.add("testItems");
        FoodServiceRequestEntry food = new FoodServiceRequestEntry(UUID.randomUUID(), "testRoom", "testStaff", "testNotes", RequestEntry.Status.PROCESSING, "testFood", "testTemp", additionalItems, "testRestrictions", "testPatient");
        Object[] values = new Object[]{food.getServiceID(), food.getRoomNumber(), food.getStaffAssignment(), food.getAdditionalNotes(), food.getStatus(), food.getFoodSelection(), food.getTempType(), food.getAdditionalItems(), food.getDietaryRestrictions(), food.getPatientName()};
        try {
            pdbController.insertQuery(TableType.FOODREQUESTS, fields, values);
        } catch (PdbController.DatabaseException e) {
            throw new RuntimeException(e);
        }
        Optional<FoodServiceRequestEntry> results = dao.get(food.getServiceID());
        FoodServiceRequestEntry daoresult = results.get();
        assertEquals(daoresult, food);
        try {
            pdbController.deleteQuery(TableType.FOODREQUESTS, "serviceID", food.getServiceID());
        } catch (PdbController.DatabaseException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void testGet() {
    }

    @Test
    void getAll() {
    }

    @Test
    void save() {
        var dao = new FoodServiceRequestEntryDaoImpl(pdbController);
        UUID uuid = UUID.randomUUID();
        List<String> additionalItems = new ArrayList<>();
        additionalItems.add("testItems");
        FoodServiceRequestEntry fsre = new FoodServiceRequestEntry(uuid, "testRoom", "testStaff", "testNotes", RequestEntry.Status.PROCESSING, "testFood", "testTemp", additionalItems, "testRestrictions", "testPatient");
        dao.save(fsre);

        FoodServiceRequestEntry updatedFsre = new FoodServiceRequestEntry(uuid, "testRoom", "updatedTestStaff", "testNotes", RequestEntry.Status.DONE, "testFood", "testTemp", additionalItems, "testRestrictions", "testPatient");
        FoodServiceRequestEntry.Field[] fields = {FoodServiceRequestEntry.Field.STAFF_ASSIGNMENT, FoodServiceRequestEntry.Field.STATUS};
        dao.update(updatedFsre, fields);

        Optional<FoodServiceRequestEntry> results = dao.get(uuid);
        FoodServiceRequestEntry daoresult = results.get();
        assertEquals(updatedFsre, daoresult);
        try {
            pdbController.deleteQuery(TableType.FOODREQUESTS, "serviceID", uuid);
        } catch (PdbController.DatabaseException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void update() {
        var values0 = new Object[]{
                UUID.randomUUID(),
                "123",
                "testStaff0",
                "testNode0",
                RequestEntry.Status.PROCESSING,
                "testFood0",
                "100",
                List.of("item1", "item2"),
                "restrictions0",
                "patientName0"
        };
        var values1 = new Object[]{
                UUID.randomUUID(),
                "123",
                "testStaff1",
                "testNode1",
                RequestEntry.Status.PROCESSING,
                "testFood1",
                "100",
                List.of("item1", "item2"),
                "restrictions1",
                "patientName1"
        };
        var values2 = new Object[]{
                UUID.randomUUID(),
                "123",
                "testStaff2",
                "testNode2",
                RequestEntry.Status.PROCESSING,
                "testFood2",
                "100",
                List.of("item1", "item2"),
                "restrictions2",
                "patientName2"
        };
        var valueSets = new Object[][]{values0, values1, values2};
        var refMap = new HashMap<UUID, FoodServiceRequestEntry>();

        for (Object[] values : valueSets) {
            try {
                pdbController.insertQuery(TableType.FOODREQUESTS, fields, values);
            } catch (PdbController.DatabaseException e) {
                assert false: "Failed to insert test data";
            }
            FoodServiceRequestEntry fsre = new FoodServiceRequestEntry(
                    (UUID) values[0],
                    (String) values[1],
                    (String) values[2],
                    (String) values[3],
                    (RequestEntry.Status) values[4],
                    (String) values[5],
                    (String) values[6],
                    (List<String>) values[7],
                    (String) values[8],
                    (String) values[9]
            );
            refMap.put(fsre.getServiceID(), fsre);
        }

        Map<UUID, FoodServiceRequestEntry> resultMap = dao.getAll();
        for (var uuid : refMap.keySet()) {
            try {
                pdbController.deleteQuery(TableType.FOODREQUESTS, "serviceID", uuid);
            } catch (PdbController.DatabaseException e) {
                assert false: "Failed to delete test data";
            }
        }

        assert resultMap.equals(refMap);

    }

    @Test
    void delete() {
        FoodServiceRequestEntry foodRequest = new FoodServiceRequestEntry(
                UUID.randomUUID(),
                "123",
                "testStaff",
                "testNode",
                RequestEntry.Status.PROCESSING,
                "testFood",
                "100",
                List.of("item1", "item2"),
                "restrictions",
                "patientName"
        );


        var values = new Object[]{
                foodRequest.getServiceID(),
                foodRequest.getRoomNumber(),
                foodRequest.getStaffAssignment(),
                foodRequest.getAdditionalNotes(),
                foodRequest.getStatus(),
                foodRequest.getFoodSelection(),
                foodRequest.getTempType(),
                foodRequest.getAdditionalItems(),
                foodRequest.getDietaryRestrictions(),
                foodRequest.getPatientName()
        };
        try {
            pdbController.insertQuery(TableType.FOODREQUESTS, fields, values);
        } catch (PdbController.DatabaseException e) {
            assert false : "Failed to insert test data";
        }

        try {
            pdbController.searchQuery(TableType.FOODREQUESTS, "serviceID", foodRequest.getServiceID());
        } catch (PdbController.DatabaseException e) {
            assert false : "Failed to find test data";
        }

        dao.delete(foodRequest);

        try {
            pdbController.searchQuery(TableType.FOODREQUESTS, "serviceID", foodRequest.getServiceID());
        } catch (PdbController.DatabaseException e) {
            assert true : "Successfully deleted test data";
        }
    }
}

