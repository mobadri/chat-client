package com.chat.client.view.client.user;

import com.chat.client.controller.client.user.HomeControllerImpl;
import com.chat.server.model.user.Mode;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.util.Callback;

import java.util.List;

public class UserStatus extends ListCell<Mode> {

    HBox hBox;
    Circle circle;
    Label label;
    private ListViewStatusController listViewStatusController;

    public UserStatus() {

        circle = new Circle();
        hBox = new HBox();
        label = new Label();
        label.setFont(Font.font("Amble CN", FontWeight.BOLD, 10));
        hBox.getChildren().addAll(circle, label);
        hBox.setSpacing(10);

    }

    @Override
    protected void updateItem(Mode mode, boolean b) {
        super.updateItem(mode, b);
        setText(null);
        setGraphic(null);
        if (mode != null && !b) {
            if (mode.equals(Mode.AVAILABLE)) {
                label.setText("Avilable");
                circle.setCenterX(8);
                circle.setRadius(8);
                circle.setFill(Color.GREEN);

            } else if (mode.equals(Mode.BUSY)) {

                label.setText("Busy");
                circle.setCenterX(8);
                circle.setRadius(8);
                circle.setFill(Color.RED);

            } else {
                label.setText("Away");
                circle.setCenterX(8);
                circle.setRadius(8);
                circle.setFill(Color.YELLOW);

            }
            setGraphic(hBox);
        }
    }

}
