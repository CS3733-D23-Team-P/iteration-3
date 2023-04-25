package edu.wpi.punchy_pegasi.frontend.components;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

public class PFXAlertCard extends VBox {
    private final PFXButton read;
    private final Label titleLabel;
    private final Label description;
    private boolean isRead;

    public PFXAlertCard() {
        super();
        titleLabel = new Label("Alert Name");
        description = new Label("A very informative description");
        read = new PFXButton("Mark read");
        isRead = false;

        getStyleClass().add("pfx-alert-card-container-unread");
        getChildren().addAll(titleLabel, description, read);
        read.getStyleClass().add("pfx-alert-card-button-unread");
        description.getStyleClass().add("pfx-alert-card-description");
        HBox.setHgrow(read, Priority.ALWAYS);
        HBox.setHgrow(description, Priority.ALWAYS);


        read.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
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
        });

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
}
