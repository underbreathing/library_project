package com.example.libraryproject.fxMethods;

import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Popup;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Toast {

    private static final int TOAST_WIDTH = 300;
    private static final int TOAST_HEIGHT = 40;

    public static void makeToast(Stage owner, String message, Duration duration) {
        Label label = new Label(message);
        label.setStyle("-fx-text-fill: white; -fx-font-size: 14px;");
        VBox vbox = new VBox(label);
        vbox.setAlignment(Pos.CENTER);
        vbox.setStyle("-fx-background-color: #333333; -fx-padding: 10px;");
        Popup popup = new Popup();
        popup.getContent().add(vbox);
        popup.setAutoHide(true);
        popup.setAnchorX(owner.getX() + owner.getWidth() / 2 - TOAST_WIDTH / 2);
        popup.setAnchorY(owner.getY() + owner.getHeight() - TOAST_HEIGHT - 10);
        popup.show(owner);
        duration = duration == null ? Duration.seconds(3) : duration;
        Duration finalDuration = duration;
        new Thread(() -> {
            try {
                Thread.sleep((long) finalDuration.toMillis());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Platform.runLater(popup::hide);
        }).start();
    }
}