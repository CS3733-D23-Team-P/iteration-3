package edu.wpi.punchy_pegasi.generated;

import edu.wpi.punchy_pegasi.App;
import edu.wpi.punchy_pegasi.backend.PdbController;
import edu.wpi.punchy_pegasi.schema.LocationName;
import java.util.Arrays;
import edu.wpi.punchy_pegasi.schema.IDao;
import edu.wpi.punchy_pegasi.schema.TableType;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.sql.SQLException;
import java.util.*;

@Slf4j
public class LocationNameDaoImpl implements IDao<java.lang.Long, LocationName, LocationNameDaoImpl.Column> {

    static String[] fields = {"uuid", "longName", "shortName", "nodeType"};
    private final PdbController dbController;

    public LocationNameDaoImpl(PdbController dbController) {
        this.dbController = dbController;
    }

    public LocationNameDaoImpl() {
        this.dbController = App.getSingleton().getPdb();
    }

    @Override
    public Optional<LocationName> get(java.lang.Long key) {
        try (var rs = dbController.searchQuery(TableType.LOCATIONNAMES, "uuid", key)) {
            rs.next();
            LocationName req = new LocationName(
                    (java.lang.Long)rs.getObject("uuid"),
                    (java.lang.String)rs.getObject("longName"),
                    (java.lang.String)rs.getObject("shortName"),
                    edu.wpi.punchy_pegasi.schema.LocationName.NodeType.valueOf((String)rs.getObject("nodeType")));
            return Optional.ofNullable(req);
        } catch (PdbController.DatabaseException | SQLException e) {
            log.error("", e);
            return Optional.empty();
        }
    }

    @Override
    public Optional<LocationName> get(Column column, Object value) {
        try (var rs = dbController.searchQuery(TableType.LOCATIONNAMES, column.name(), value)) {
            rs.next();
            LocationName req = new LocationName(
                    (java.lang.Long)rs.getObject("uuid"),
                    (java.lang.String)rs.getObject("longName"),
                    (java.lang.String)rs.getObject("shortName"),
                    edu.wpi.punchy_pegasi.schema.LocationName.NodeType.valueOf((String)rs.getObject("nodeType")));
            return Optional.ofNullable(req);
        } catch (PdbController.DatabaseException | SQLException e) {
            log.error("", e);
            return Optional.empty();
        }
    }

    @Override
    public Map<java.lang.Long, LocationName> getAll() {
        var map = new HashMap<java.lang.Long, LocationName>();
        try (var rs = dbController.searchQuery(TableType.LOCATIONNAMES)) {
            while (rs.next()) {
                LocationName req = new LocationName(
                    (java.lang.Long)rs.getObject("uuid"),
                    (java.lang.String)rs.getObject("longName"),
                    (java.lang.String)rs.getObject("shortName"),
                    edu.wpi.punchy_pegasi.schema.LocationName.NodeType.valueOf((String)rs.getObject("nodeType")));
                if (req != null)
                    map.put(req.getUuid(), req);
            }
        } catch (PdbController.DatabaseException | SQLException e) {
            log.error("", e);
        }
        return map;
    }

    @Override
    public void save(LocationName locationName) {
        Object[] values = {locationName.getUuid(), locationName.getLongName(), locationName.getShortName(), locationName.getNodeType()};
        try {
            dbController.insertQuery(TableType.LOCATIONNAMES, fields, values);
        } catch (PdbController.DatabaseException e) {
            log.error("Error saving", e);
        }

    }

    @Override
    public void update(LocationName locationName, Column[] params) {
        Object[] values = {locationName.getUuid(), locationName.getLongName(), locationName.getShortName(), locationName.getNodeType()};
        List<Object> pruned = new ArrayList<>();
        for(var column : params)
            pruned.add(values[Arrays.asList(Column.values()).indexOf(column)]);
        try {
            dbController.updateQuery(TableType.LOCATIONNAMES, "uuid", locationName.getUuid(), (String[])Arrays.stream(params).map(p->p.getColName()).toArray(), pruned.toArray());
        } catch (PdbController.DatabaseException e) {
            log.error("Error saving", e);
        }
    }

    @Override
    public void delete(LocationName locationName) {
        try {
            dbController.deleteQuery(TableType.LOCATIONNAMES, "uuid", locationName.getUuid());
        } catch (PdbController.DatabaseException e) {
            log.error("Error deleting", e);
        }
    }

    @RequiredArgsConstructor
    public enum Column {
        UUID("uuid"),
        LONG_NAME("longName"),
        SHORT_NAME("shortName"),
        NODE_TYPE("nodeType");
        @Getter
        private final String colName;
    }
}