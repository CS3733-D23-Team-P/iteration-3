package edu.wpi.punchy_pegasi.frontend.components;

import edu.wpi.punchy_pegasi.App;
import edu.wpi.punchy_pegasi.schema.Alert;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

import java.util.Map;
import java.util.Optional;

public class PFXAlertCard extends VBox {
    private final PFXButton read;
    private final Label titleLabel;
    private final Label description;
    private boolean isRead;
    private Long uuid;

    public PFXAlertCard(Long uuid) {
        super();
        Optional<Alert> la = App.getSingleton().getFacade().getAlert(uuid);
        Alert alert = la.get();
        Long longa = alert.getUuid();
        //service request or move based on what
        //Service Request
        //Move
        titleLabel = new Label(alert.getAlertTitle());
        //titleLabelS = new Label("Service Request");
        //what you assigned to or when the move is happening / what unit
            //You have been assigned to
            //The -unit- is moving -when-
        description = new Label(alert.getDescription());
        read = new PFXButton("Mark Read");
        isRead = false;

        getStyleClass().add("pfx-alert-card-container-unread");
        getChildren().addAll(titleLabel, description, read);
        HBox.setHgrow(read, Priority.ALWAYS);
        HBox.setHgrow(description, Priority.ALWAYS);
        read.setOnAction(e -> toggleRead());

    }

    public PFXAlertCard(String title, String description, boolean isRead) {
        super();
        this.titleLabel = new Label(title);
        this.description = new Label(description);
        this.read = new PFXButton("Mark read");
        this.isRead = isRead;

        getStyleClass().add("pfx-alert-card-container-unread");
        this.description.getStyleClass().add("pfx-alert-card-description");
        read.getStyleClass().add("pfx-alert-card-read-button");
        HBox.setHgrow(read, Priority.ALWAYS);
        HBox.setHgrow(this.description, Priority.ALWAYS);
        getChildren().addAll(this.titleLabel, this.description, read);

    }

    public String getTitle() {
        return titleLabel.getText();
    }
    public String getDescription(){ return description.getText(); }
    public Boolean getIsRead(){ return isRead;}

    public void toggleRead() {
        isRead = !isRead;
        if(isRead == true) {
            read.setText("Mark unread");
            read.getStyleClass().add("pfx-alert-card-read");
            getStyleClass().add("pfx-alert-card-container-read");
        }
        else{
            read.setText("Mark read");
            getStyleClass().remove("pfx-alert-card-container-read");
        }
    }
}
