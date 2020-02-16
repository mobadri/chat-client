package com.chat.client.view.client.notification;

import com.chat.server.model.chat.Notification;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.Callback;

public class NotificationCellRenderer implements Callback<ListView<Notification>, ListCell<Notification>> {

    @Override
    public ListCell<Notification> call(ListView<Notification> p) {

        ListCell<Notification> cell = new ListCell<Notification>() {
            @Override
            protected void updateItem(Notification notification, boolean b) {
                super.updateItem(notification, b);
                setGraphic(null);
                setText(null);
                if (notification != null) {
                    VBox vBox = new VBox();
                    HBox hBox = new HBox();
                    Label notificationFrom = new Label(notification.getUserFrom().getFirstName()+" "
                            +notification.getUserFrom().getLastName());
                    Label notificationContent = new Label(notification.getNotificationMessage());
                    vBox.getChildren().addAll(notificationFrom,notificationContent);
                    ImageView pictureImageView = new ImageView();
                    Image image = new Image(getClass().getResource("/static/images/Smile.png").toString(), 50, 50, true, true);
                    pictureImageView.setImage(image);
                    hBox.getChildren().addAll(pictureImageView,vBox);
                    hBox.setAlignment(Pos.CENTER_LEFT);
                    setGraphic(hBox);
                }
            }
        };
        return cell;
    }
}