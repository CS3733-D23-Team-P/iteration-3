package edu.wpi.punchy_pegasi.frontend.components;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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
        read = new PFXButton();
        isRead = false;


        getStyleClass().add("pfx-alert-card-container");
        description.getStyleClass().add("pfx-alert-card-description");
        read.getStyleClass().add("pfx-alert-card-read-button");
        HBox.setHgrow(read, Priority.ALWAYS);
        HBox.setHgrow(description, Priority.ALWAYS);
        getChildren().addAll(titleLabel, description, read);

        read.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                isRead = true;
            }
        });

    }

    public PFXAlertCard(String title, String description, boolean isRead) {
        super();
        this.titleLabel = new Label(title);
        this.description = new Label(description);
        this.read = new PFXButton();
        this.isRead = isRead;

        getStyleClass().add("pfx-alert-card-container");
        this.description.getStyleClass().add("pfx-alert-card-description");
        read.getStyleClass().add("pfx-alert-card-read-button");
        HBox.setHgrow(read, Priority.ALWAYS);
        HBox.setHgrow(this.description, Priority.ALWAYS);
        getChildren().addAll(this.titleLabel, this.description, read);

        read.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                isRead = true;
            }
        });
    }

    public String getTitle() {
        return titleLabel.getText();
    }
    public String getDescription(){ return description.getText(); }
    public Boolean isRead(){ return isRead;}
}
