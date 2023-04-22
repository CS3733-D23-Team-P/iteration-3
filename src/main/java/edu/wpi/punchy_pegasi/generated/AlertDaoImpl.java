package edu.wpi.punchy_pegasi.generated;

import edu.wpi.punchy_pegasi.App;
import edu.wpi.punchy_pegasi.backend.PdbController;
import edu.wpi.punchy_pegasi.schema.Alert;
import edu.wpi.punchy_pegasi.schema.IDao;
import edu.wpi.punchy_pegasi.schema.TableType;
import lombok.extern.slf4j.Slf4j;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Slf4j
public class AlertDaoImpl implements IDao<java.lang.Long, Alert, Alert.Field> {

    static String[] fields = {"uuid", "alertTitle", "description", "readStatus"};
    private final PdbController dbController;

    public AlertDaoImpl(PdbController dbController) {
        this.dbController = dbController;
    }

    public AlertDaoImpl() {
        this.dbController = App.getSingleton().getPdb();
    }

    @Override
    public Optional<Alert> get(java.lang.Long key) {
        try (var rs = dbController.searchQuery(TableType.ALERTS, "uuid", key)) {
            rs.next();
            Alert req = new Alert(
                    rs.getObject("uuid", java.lang.Long.class),
                    rs.getObject("alertTitle", java.lang.String.class),
                    rs.getObject("description", java.lang.String.class),
                    rs.getObject("readStatus", java.lang.Boolean.class));
            return Optional.ofNullable(req);
        } catch (PdbController.DatabaseException | SQLException e) {
            log.error("", e);
            return Optional.empty();
        }
    }

    @Override
    public Map<java.lang.Long, Alert> get(Alert.Field column, Object value) {
        return get(new Alert.Field[]{column}, new Object[]{value});
    }

    @Override
    public Map<java.lang.Long, Alert> get(Alert.Field[] params, Object[] value) {
        var map = new HashMap<java.lang.Long, Alert>();
        try (var rs = dbController.searchQuery(TableType.ALERTS, Arrays.stream(params).map(Alert.Field::getColName).toList().toArray(new String[params.length]), value)) {
            while (rs.next()) {
                Alert req = new Alert(
                    rs.getObject("uuid", java.lang.Long.class),
                    rs.getObject("alertTitle", java.lang.String.class),
                    rs.getObject("description", java.lang.String.class),
                    rs.getObject("readStatus", java.lang.Boolean.class));
                if (req != null)
                    map.put(req.getUuid(), req);
            }
        } catch (PdbController.DatabaseException | SQLException e) {
            log.error("", e);
        }
        return map;
    }

    @Override
    public Map<java.lang.Long, Alert> getAll() {
        var map = new HashMap<java.lang.Long, Alert>();
        try (var rs = dbController.searchQuery(TableType.ALERTS)) {
            while (rs.next()) {
                Alert req = new Alert(
                    rs.getObject("uuid", java.lang.Long.class),
                    rs.getObject("alertTitle", java.lang.String.class),
                    rs.getObject("description", java.lang.String.class),
                    rs.getObject("readStatus", java.lang.Boolean.class));
                if (req != null)
                    map.put(req.getUuid(), req);
            }
        } catch (PdbController.DatabaseException | SQLException e) {
            log.error("", e);
        }
        return map;
    }

    @Override
    public void save(Alert alert) {
        Object[] values = {alert.getUuid(), alert.getAlertTitle(), alert.getDescription(), alert.getReadStatus()};
        try {
            dbController.insertQuery(TableType.ALERTS, fields, values);
        } catch (PdbController.DatabaseException e) {
            log.error("Error saving", e);
        }

    }

    @Override
    public void update(Alert alert, Alert.Field[] params) {
        if (params.length < 1)
            return;
        try {
            dbController.updateQuery(TableType.ALERTS, "uuid", alert.getUuid(), Arrays.stream(params).map(Alert.Field::getColName).toList().toArray(new String[params.length]), Arrays.stream(params).map(p -> p.getValue(alert)).toArray());
        } catch (PdbController.DatabaseException e) {
            log.error("Error saving", e);
        }
    }

    @Override
    public void delete(Alert alert) {
        try {
            dbController.deleteQuery(TableType.ALERTS, "uuid", alert.getUuid());
        } catch (PdbController.DatabaseException e) {
            log.error("Error deleting", e);
        }
    }
}