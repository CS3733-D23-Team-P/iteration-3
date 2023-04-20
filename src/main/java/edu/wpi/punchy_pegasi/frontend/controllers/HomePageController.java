package edu.wpi.punchy_pegasi.frontend.controllers;

import edu.wpi.punchy_pegasi.App;
import edu.wpi.punchy_pegasi.frontend.components.PFXButton;
import edu.wpi.punchy_pegasi.generated.Facade;
import edu.wpi.punchy_pegasi.schema.*;
import io.github.palexdev.materialfx.controls.MFXComboBox;
import io.github.palexdev.materialfx.controls.MFXTableColumn;
import io.github.palexdev.materialfx.controls.MFXTableView;
import io.github.palexdev.materialfx.controls.cell.MFXTableRowCell;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;

import java.util.*;

@Slf4j
public class HomePageController {
    @FXML
    MFXTableView<GenericRequestEntry> requestTable;
    @FXML
    private VBox tableContainer;
    private final Facade facade = App.getSingleton().getFacade();
    private final Map<Long, LocationName> locationNames = facade.getAllLocationName();
    private final Map<Long, Employee> employees = facade.getAllEmployee();

    @FXML
    private MFXComboBox notificationComboBox;

    @FXML
    private LineChart lineChart;

    @FXML
    PFXButton openWindow;

    @FXML
    private void initializeLineChart(){
        XYChart.Series<Number, Number> series = new XYChart.Series<>();
        series.setName("Placeholder Name");

        //place holder data
        series.getData().add(new XYChart.Data<>(1, 10));
        series.getData().add(new XYChart.Data<>(2, 20));
        series.getData().add(new XYChart.Data<>(3, 30));
        series.getData().add(new XYChart.Data<>(4, 40));
        series.getData().add(new XYChart.Data<>(5, 50));

        lineChart.getData().add(series);
    }
//    @FXML
//    private void initialize() {
//        showServiceRequestTable(true);
//        initRequestTable();
//    }

//    private void showServiceRequestTable(boolean show) {
//        requestTable.setVisible(show);
//        requestTable.setManaged(show);
//    }

    @FXML
    private void openSelectedWindow() {
        String selectedOption = (String) notificationComboBox.getValue();
        if (selectedOption != null) {
            if (selectedOption.equals("Meals")) {
                Stage window = new Stage();
                Scene scene = new Scene(new MFXTableView<>());
                window.setTitle(selectedOption + " Window");
                window.setScene(scene);
                window.show();
            } else if (selectedOption.equals("Flowers")) {
                Stage window = new Stage();
                Scene scene = new Scene(new MFXTableView<>());
                window.setTitle(selectedOption + " Window");
                window.setScene(scene);
                window.show();
            } else if (selectedOption.equals("Conference Room")) {
                Stage window = new Stage();
                Scene scene = new Scene(new MFXTableView<>());
                window.setTitle(selectedOption + " Window");
                window.setScene(scene);
                window.show();
            } else if (selectedOption.equals("Office Supplies")) {
                Stage window = new Stage();
                Scene scene = new Scene(new MFXTableView<>());
                window.setTitle(selectedOption + " Window");
                window.setScene(scene);
                window.show();
            } else if (selectedOption.equals("Furniture")) {
                Stage window = new Stage();
                Scene scene = new Scene(new MFXTableView<>());
                window.setTitle(selectedOption + " Window");
                window.setScene(scene);
                window.show();
            }
        }
    }
//            Stage window = new Stage();
//            Scene scene = new Scene(new MFXTableView<>());
//            window.setTitle(selectedOption + " Window");
//            window.setScene(scene);
//            window.show();

    private void rowClicked(GenericRequestEntry entry) {
        var original = entry.originalEntry;
        var tt = Arrays.stream(TableType.values()).filter(f -> f.getClazz() == original.getClass()).findFirst().get();
    }

    private void initRequestTable() {
        var employeeID = App.getSingleton().getAccount().getEmployeeID();
        List<RequestEntry> requestEntries = new ArrayList<>();
        requestEntries.addAll(facade.getAllFurnitureRequestEntry().values());
        requestEntries.addAll(facade.getAllConferenceRoomEntry().values());
        requestEntries.addAll(facade.getAllFlowerDeliveryRequestEntry().values());
        requestEntries.addAll(facade.getAllOfficeServiceRequestEntry().values());
        requestEntries.addAll(facade.getAllFoodServiceRequestEntry().values());

        ObservableList<GenericRequestEntry> requestList = FXCollections.observableArrayList(requestEntries.stream()
                .filter(e -> App.getSingleton().getAccount().getAccountType().getShieldLevel() >= Account.AccountType.ADMIN.getShieldLevel() || Objects.equals(e.getStaffAssignment(), employeeID))
                .map(GenericRequestEntry::new)
                .toList());

        MFXTableColumn<GenericRequestEntry> locationCol = new MFXTableColumn<>("Location", true);
        MFXTableColumn<GenericRequestEntry> employeeCol = new MFXTableColumn<>("Employee", true);
        MFXTableColumn<GenericRequestEntry> additionalCol = new MFXTableColumn<>("Additional Notes", true);
        MFXTableColumn<GenericRequestEntry> statusCol = new MFXTableColumn<>("Status", true);
        MFXTableColumn<GenericRequestEntry> typeCol = new MFXTableColumn<>("Request Type", true);
        locationCol.setRowCellFactory(r -> new MFXTableRowCell<>(r2 -> r2.location));
        employeeCol.setRowCellFactory(r -> new MFXTableRowCell<>(r2 -> r2.assigned));
        additionalCol.setRowCellFactory(r -> new MFXTableRowCell<>(r2 -> r2.additionalNotes));
        statusCol.setRowCellFactory(r -> new MFXTableRowCell<>(r2 -> r2.status));
        typeCol.setRowCellFactory(r -> new MFXTableRowCell<>(r2 -> r2.tableType));
//        requestTable.setTableRowFactory(r -> {
//            var row = new MFXTableRow<>(requestTable, r);
//            row.setStyle("-fx-background-color: red");
//            row.setOnMouseClicked(e -> {
//                rowClicked(r);
//            });
//            return row;
//        });
        requestTable.getTableColumns().addAll(locationCol, employeeCol, additionalCol, statusCol, typeCol);
        requestTable.setItems(requestList);
        requestTable.prefWidthProperty().bind(tableContainer.widthProperty());
        requestTable.prefHeightProperty().bind(tableContainer.heightProperty());
        requestTable.autosizeColumnsOnInitialization();
    }

    @FXML
    private void resizeColumns() {
        requestTable.autosizeColumns();
    }

    private class GenericRequestEntry {
        RequestEntry originalEntry;
        String location;
        String assigned;
        String additionalNotes;
        RequestEntry.Status status;
        TableType tableType;

        GenericRequestEntry(RequestEntry re) {
            originalEntry = re;
            location = locationNames.getOrDefault(re.getLocationName(), new LocationName(null, "Unknown location", "", null)).getLongName();
            assigned = employees.getOrDefault(re.getStaffAssignment(), new Employee(0L, "Unknown", "Employee")).getFullName();
            additionalNotes = re.getAdditionalNotes();
            status = re.getStatus();
            tableType = Arrays.stream(TableType.values())
                    .filter(tt -> tt.getClazz() == re.getClass())
                    .findFirst()
                    .orElseGet(() -> TableType.GENERIC);
        }
    }
}