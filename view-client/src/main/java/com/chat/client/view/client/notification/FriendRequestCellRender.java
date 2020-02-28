package com.chat.client.view.client.notification;

import com.chat.client.view.client.chat.render.RenderImage;
import com.chat.server.model.user.User;
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

public class FriendRequestCellRender implements Callback<ListView<User>, ListCell<User>> {

    FriendRequestListViewController controller;
    private RenderImage renderImage = new RenderImage();

    @Override
    public ListCell<User> call(ListView<User> userListView) {

        ListCell<User> cell = new ListCell<User>(){

            @Override
            protected void updateItem(User user, boolean b) {
                super.updateItem(user, b);
                setGraphic(null);
                setText(null);
                JFXButton confirm = new JFXButton();
                JFXButton delete = new JFXButton();
                Circle circle = new Circle();
                if(user != null){
                    VBox vBox = new VBox();
                    HBox hBox = new HBox();
                    HBox hBox1 = new HBox();
                    hBox.setSpacing(10);
                    hBox.setStyle("-fx-background-color: white  ;" +
                            "-fx-padding: 10;" + "-fx-border-style: solid inside;"
                            + "-fx-border-width: 2;" + "-fx-border-insets: 5;"
                            + "-fx-border-radius: 5;" + "-fx-border-color: gray;");

                    Label userFrom = new Label(user.getFirstName()+" "+user.getLastName());
                    userFrom.setTextFill(Color.WHITE);
                    userFrom.setFont(Font.font(12));
                    userFrom.setTextFill(Color.BLACK);

                    Label requestContent = new Label("Sent you a friend request");
                    requestContent.setTextFill(Color.WHITE);
                    requestContent.setFont(Font.font(11));
                    requestContent.setTextFill(Color.BLACK);


                    confirm.setText("Confirm");
                    confirm.setTextAlignment(TextAlignment.CENTER);
                    confirm.setStyle("-fx-background-color: lightgrey ;" +
                            "  -fx-text-fill: dimgray;");
                    handleConfirmButton(user, confirm);


                    delete.setText("Delete");
                    delete.setTextAlignment(TextAlignment.CENTER);
                    delete.setStyle("-fx-background-color: lightgrey ;" +
                            "  -fx-text-fill: dimgray;");
                    handleDeleteButton(user, delete);

                    hBox1.getChildren().addAll(confirm, delete);
                    hBox1.setSpacing(8);

                    vBox.getChildren().addAll(userFrom, requestContent, hBox1);
                    vBox.setPadding(new Insets(0, 0, 0, 0));
                    vBox.layout();
                    circle.setCenterX(32);
                    circle.setRadius(32);
                    ImageView statusImageView = new ImageView();
                    Image statusImage = new Image(getClass()
                            .getResource("/static/images/mode/" + user.getMode().toString().toLowerCase().trim() + ".png").toString(), 36, 36, true, true);
                    statusImageView.setImage(statusImage);
                    Image image = renderImage.convertToImage(user.getImage());
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

    public void handleConfirmButton(User user, Button button) {
        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {

               // System.out.println(user.getUserFrom());
            }
        });
    }

    public void handleDeleteButton(User user, Button button) {
        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                //System.out.println(notification.getUserFrom());
                controller.removeFriendRequestFromUI(user);
            }
        });
    }

    public void setController(FriendRequestListViewController controller) {

        this.controller = controller;
    }
}
