package edu.wpi.punchy_pegasi.frontend.components;

import edu.wpi.punchy_pegasi.App;
import edu.wpi.punchy_pegasi.frontend.icons.MaterialSymbols;
import edu.wpi.punchy_pegasi.frontend.icons.PFXIcon;
import edu.wpi.punchy_pegasi.schema.Alert;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public class PFXAlertCard extends HBox {
    private final PFXButton read;
    private final Label titleLabel;
    private final Label description;
    private boolean isRead;
    private Long uuid;
    private VBox textContainer = new VBox();

    public PFXAlertCard(Alert alert) {
        super();
        Alert.ReadStatus readStatus = alert.getReadStatus();
        if(readStatus == Alert.ReadStatus.READ) {
            isRead = true;
            getStyleClass().add("pfx-alert-card-container-read");
        } else {
            isRead = false;
            getStyleClass().add("pfx-alert-card-container-unread");
        }
        titleLabel = new Label(alert.getAlertTitle());
        description = new Label(alert.getDescription());
        read = new PFXButton("", new PFXIcon(MaterialSymbols.NOTIFICATIONS));


        getChildren().addAll(textContainer, read);
        textContainer.getChildren().addAll(titleLabel, description);
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
        if(isRead) {
            getStyleClass().remove("pfx-alert-card-container-unread");
            getStyleClass().add("pfx-alert-card-container-read");
        }
        else{
            getStyleClass().remove("pfx-alert-card-container-read");
            getStyleClass().add("pfx-alert-card-container-unread");
        }
    }
}
