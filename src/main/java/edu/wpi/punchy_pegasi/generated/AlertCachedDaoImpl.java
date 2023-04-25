package edu.wpi.punchy_pegasi.generated;

import edu.wpi.punchy_pegasi.App;
import edu.wpi.punchy_pegasi.backend.PdbController;
import edu.wpi.punchy_pegasi.schema.Alert;
import edu.wpi.punchy_pegasi.schema.IDao;
import edu.wpi.punchy_pegasi.schema.TableType;
import javafx.collections.FXCollections;
import javafx.collections.MapChangeListener;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import lombok.extern.slf4j.Slf4j;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.sql.SQLException;
import java.util.*;

@Slf4j
public class AlertCachedDaoImpl implements IDao<java.lang.Long, Alert, Alert.Field>, PropertyChangeListener {

    static String[] fields = {"uuid", "alertTitle", "description", "readStatus"};

    private final ObservableMap<java.lang.Long, Alert> cache = FXCollections.observableMap(new LinkedHashMap<>());
    private final ObservableList<Alert> list = FXCollections.observableArrayList();
    private final PdbController dbController;

    public AlertCachedDaoImpl(PdbController dbController) {
        this.dbController = dbController;
        cache.addListener((MapChangeListener<java.lang.Long, Alert>) c -> {
            if (c.wasRemoved()) {
                list.remove(c.getValueRemoved());
            } else if (c.wasAdded()) {
                list.add(c.getValueAdded());
            }
        });
        initCache();
        this.dbController.addPropertyChangeListener(this);
    }

    public void add(Alert alert) {
        if (!cache.containsKey(alert.getUuid()))
            cache.put(alert.getUuid(), alert);
    }

    public void update(Alert alert) {
        cache.put(alert.getUuid(), alert);
    }

    public void remove(Alert alert) {
        cache.remove(alert.getUuid());
    }

    private void initCache() {
        try (var rs = dbController.searchQuery(TableType.ALERTS)) {
            while (rs.next()) {
                Alert req = new Alert(
                    rs.getObject("uuid", java.lang.Long.class),
                    rs.getObject("alertTitle", java.lang.String.class),
                    rs.getObject("description", java.lang.String.class),
                    rs.getObject("readStatus", java.lang.Boolean.class));
                add(req);
            }
        } catch (PdbController.DatabaseException | SQLException e) {
            log.error("", e);
        }
    }

    @Override
    public Optional<Alert> get(java.lang.Long key) {
        return Optional.ofNullable(cache.get(key));
    }

    @Override
    public Map<java.lang.Long, Alert> get(Alert.Field column, Object value) {
        return get(new Alert.Field[]{column}, new Object[]{value});
    }

    @Override
    public Map<java.lang.Long, Alert> get(Alert.Field[] params, Object[] value) {
        var map = new HashMap<java.lang.Long, Alert>();
        if (params.length != value.length) return map;
        cache.values().forEach(v -> {
            var include = true;
            for (int i = 0; i < params.length; i++)
                include &= Objects.equals(params[i].getValue(v), value[i]);
            if (include)
                map.put(v.getUuid(), v);
        });
        return map;
    }

    @Override
    public ObservableMap<java.lang.Long, Alert> getAll() {
        return cache;
    }

    @Override
    public ObservableList<Alert> getAllAsList() {
        return list;
    }

    @Override
    public void save(Alert alert) {
        Object[] values = {alert.getUuid(), alert.getAlertTitle(), alert.getDescription(), alert.getReadStatus()};
        try {
            dbController.insertQuery(TableType.ALERTS, fields, values);
//            add(alert);
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
//            update(alert);
        } catch (PdbController.DatabaseException e) {
            log.error("Error saving", e);
        }
    }

    @Override
    public void delete(Alert alert) {
        try {
            dbController.deleteQuery(TableType.ALERTS, "uuid", alert.getUuid());
//            remove(alert);
        } catch (PdbController.DatabaseException e) {
            log.error("Error deleting", e);
        }
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (Objects.equals(evt.getPropertyName(), TableType.ALERTS.name() + "_update")) {
            var update = (PdbController.DatabaseChangeEvent) evt.getNewValue();
            var data = (Alert) update.data();
            switch (update.action()) {
                case UPDATE -> update(data);
                case DELETE -> remove(data);
                case INSERT -> add(data);
            }
        }
    }
}