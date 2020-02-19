package com.chat.client.view.client.notification;

import com.chat.client.view.client.chat.UserHome;
import com.chat.server.model.chat.Notification;
import com.jfoenix.controls.JFXButton;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.util.Callback;

public class NotificationCellRenderer implements Callback<ListView<Notification>, ListCell<Notification>> {

    NotificationViewListController controller = new NotificationViewListController();

    @Override
    public ListCell<Notification> call(ListView<Notification> p) {

        ListCell<Notification> cell = new ListCell<Notification>() {
            @Override
            protected void updateItem(Notification notification, boolean b) {
                super.updateItem(notification, b);
                setGraphic(null);
                setText(null);
                JFXButton view = new JFXButton();
                JFXButton remove = new JFXButton();
                Circle circle = new Circle();
                if (notification != null) {
                    VBox vBox = new VBox();
                    HBox hBox = new HBox();
                    HBox hBox1 = new HBox();
                    hBox.setSpacing(10);
                    hBox.setStyle("-fx-background-color: white  ;" +
                            "-fx-padding: 10;" + "-fx-border-style: solid inside;"
                            + "-fx-border-width: 2;" + "-fx-border-insets: 5;"
                            + "-fx-border-radius: 5;" + "-fx-border-color: gray;");

                    Label notificationFrom = new Label(notification.getUserFrom().getFirstName()+" "
                            +notification.getUserFrom().getLastName());
                    notificationFrom.setTextFill(Color.WHITE);
                    notificationFrom.setFont(Font.font(12));
                    notificationFrom.setTextFill(Color.BLACK);

                    Label notificationContent = new Label(notification.getNotificationMessage());
                    notificationContent.setTextFill(Color.WHITE);
                    notificationContent.setFont(Font.font(11));
                    notificationContent.setTextFill(Color.BLACK);


                    view.setText(" View ");
                    view.setTextAlignment(TextAlignment.CENTER);
                    view.setStyle("-fx-background-color: lightgrey ;" +
                            "  -fx-text-fill: dimgray;" );
                    handleViewButton(notification,view);


                    remove.setText("Remove");
                    remove.setTextAlignment(TextAlignment.CENTER);
                    remove.setStyle("-fx-background-color: lightgrey ;" +
                            "  -fx-text-fill: dimgray;" );
                    handleRemoveButton(notification,remove);

                    hBox1.getChildren().addAll(view,remove);
                    hBox1.setSpacing(8);

                    vBox.getChildren().addAll(notificationFrom,notificationContent,hBox1);
                    vBox.setPadding(new Insets(0,0,0,0));
                    vBox.layout();
                    ImageView pictureImageView = new ImageView();
                   /* pictureImageView.setStyle("width: 24px;\n" +
                            "    height: 24px;\n" +
                            "    border-radius: 50%;\n" +
                            "    border-style: solid;\n" +
                            "    border-width: 1px;\n" +
                            "    border-color: lightgrey;");*/
                    circle.setCenterX(22);
                    circle.setRadius(22);
                    Image image = new Image(getClass().getResource("/static/images/baby.png").toString(), 50, 50, true, true);
                    circle.setFill(new ImagePattern(image));
//                    pictureImageView.setImage(image);
                    hBox.getChildren().addAll(circle,vBox);
                    //hBox.getChildren().addAll(pictureImageView,vBox);
                    hBox.setAlignment(Pos.CENTER_LEFT);
                    setPrefWidth(200);
                    hBox.setMaxWidth(220);
                    hBox.setMinWidth(220);
                    setGraphic(hBox);
                }
            }
        };
        return cell;
    }

    public void handleViewButton(Notification notification,Button button){
        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {

                System.out.println(notification.getUserFrom());
            }
        });
    }

    public void handleRemoveButton(Notification notification,Button button){
        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                //System.out.println(notification.getUserFrom());
                controller.removeNotificationFromUI(notification);
            }
        });
    }

}