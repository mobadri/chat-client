package com.chat.client.view.client.notification;

import com.chat.client.controller.client.fileTransfer.FileTranseferController;
import com.chat.client.controller.client.fileTransfer.FileTranseferControllerImpl;
import com.chat.client.view.client.chat.render.RenderImage;
import com.chat.server.model.chat.Notification;
import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
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
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.util.Callback;
import org.w3c.dom.ls.LSOutput;

import java.rmi.RemoteException;
import java.util.StringTokenizer;

public class NotificationCellRenderer implements Callback<ListView<Notification>, ListCell<Notification>> {

    private NotificationViewListController controller;

    public NotificationCellRenderer() {
    }

    private RenderImage renderImage = new RenderImage();

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

                    Label notificationFrom = new Label(notification.getUserFrom().getFirstName() + " "
                            + notification.getUserFrom().getLastName());
                    notificationFrom.setTextFill(Color.WHITE);
                    notificationFrom.setFont(Font.font(12));
                    notificationFrom.setTextFill(Color.BLACK);

                    Label notificationContent = new Label(notification.getNotificationMessage().substring(2));
                    notificationContent.setTextFill(Color.WHITE);
                    notificationContent.setFont(Font.font(11));
                    notificationContent.setTextFill(Color.BLACK);


                    view.setText(" View ");
                    view.setTextAlignment(TextAlignment.CENTER);
                    view.setStyle("-fx-background-color: lightgrey ;" +
                            "  -fx-text-fill: dimgray;");
                    handleViewButton(notification, view);


                    remove.setText("Remove");
                    remove.setTextAlignment(TextAlignment.CENTER);
                    remove.setStyle("-fx-background-color: lightgrey ;" +
                            "  -fx-text-fill: dimgray;");
                    handleRemoveButton(notification, remove);

                    hBox1.getChildren().addAll(view, remove);
                    hBox1.setSpacing(8);

                    vBox.getChildren().addAll(notificationFrom, notificationContent, hBox1);
                    vBox.setPadding(new Insets(0, 0, 0, 0));
                    vBox.layout();
                    ImageView pictureImageView = new ImageView();
                   /* pictureImageView.setStyle("width: 24px;\n" +
                            "    height: 24px;\n" +
                            "    border-radius: 50%;\n" +
                            "    border-style: solid;\n" +
                            "    border-width: 1px;\n" +
                            "    border-color: lightgrey;");*/
                    circle.setCenterX(32);
                    circle.setRadius(32);
                    //-------------------------------------
                    ImageView statusImageView = new ImageView();
                    Image statusImage = new Image(getClass()
//                            .getResource("/static/images/mode/available.png").toString(), 16, 16, true, true);
                            .getResource("/static/images/mode/" + notification.getUserFrom().getMode().toString().toLowerCase().trim() + ".png").toString(), 36, 36, true, true);
                    statusImageView.setImage(statusImage);
                    Image image = renderImage.convertToImage(notification.getUserFrom().getImage());
                    if (image == null) {
                        image = new Image(getClass().getResource("/static/images/Smile.png").toString(), 40, 40, true, false);
                    }

                    StackPane stackPane = new StackPane();

                    stackPane.setMargin(statusImageView, new Insets(32, 0, 0, 32));

                    circle.setFill(new ImagePattern(image));
                    stackPane.getChildren().addAll(circle, statusImageView);

                    hBox.getChildren().addAll(stackPane, vBox);
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

    public void handleViewButton(Notification notification, Button button) {
        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                System.out.println("\n mariam file \n");
                System.out.println(notification.getUserFrom());
                switch (notification.getNotificationType()) {
                    case FILE_TRANSFER_ACCEPT:
                        String messegeContent = notification.getNotificationMessage();
                        String fileName = messegeContent.substring(messegeContent.indexOf(":=") + 2);
                        controller.clientAcceptFile(fileName, 1,
                                notification.getUserTo());
                }
            }
        });
    }

    public void handleRemoveButton(Notification notification, Button button) {
        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                //System.out.println(notification.getUserFrom());
                controller.removeNotificationFromUI(notification);
            }
        });
    }

    public void setController(NotificationViewListController controller) {
        this.controller = controller;
    }
}