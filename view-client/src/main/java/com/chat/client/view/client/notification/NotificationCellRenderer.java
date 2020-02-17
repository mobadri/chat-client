package com.chat.client.view.client.notification;

import com.chat.server.model.chat.Notification;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.shape.SVGPath;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
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
                    HBox hBox1 = new HBox();
                    hBox.setSpacing(10);
                    hBox.setStyle("-fx-padding: 10;" + "-fx-border-style: solid inside;"
                            + "-fx-border-width: 2;" + "-fx-border-insets: 5;"
                            + "-fx-border-radius: 5;" + "-fx-border-color: green;");

                    Label notificationFrom = new Label(notification.getUserFrom().getFirstName()+" "
                            +notification.getUserFrom().getLastName());
                    notificationFrom.setTextFill(Color.FUCHSIA);
                    //notificationFrom.setFont(new Font());
                    Label notificationContent = new Label(notification.getNotificationMessage());
                    Button view = new Button();
//                    view.setPadding(new Insets(5,16,5,5));
                    view.setText(" View  ");
                    view.setTextAlignment(TextAlignment.CENTER);
                    view.setStyle("-fx-background-color: green;" +
                            "  -fx-text-fill: white;" +
                            "  -fx-background-radius: 16px;");
                    Button remove = new Button();
//                    remove.setPadding(new Insets(5,5,5,15));
                    remove.setText("Remove");
                    remove.setTextAlignment(TextAlignment.CENTER);
                    remove.setStyle("-fx-background-color: gray;" +
                            "  -fx-text-fill: white;" +
                            "  -fx-background-radius: 16px;");
                    hBox1.getChildren().addAll(view,remove);
                    vBox.getChildren().addAll(notificationFrom,notificationContent,hBox1);
                    vBox.setPadding(new Insets(0,0,0,15));
                    vBox.layout();
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