package com.chat.client.view.client.chat.render;

import com.chat.client.controller.client.user.HomeControllerImpl;
import com.chat.server.model.chat.ChatGroup;
import com.chat.server.model.user.User;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.util.Callback;

public class ChatRendererInviteFriend implements Callback<ListView<User>, ListCell<User>> {
    User currentUser;
    ChatGroup currentChatGroup;
    Button InviteFriend;
    private RenderImage renderImage = new RenderImage();

    @Override
    public ListCell<User> call(ListView<User> p) {

        ListCell<User> cell = new ListCell<User>() {

            @Override
            protected void updateItem(User user, boolean bln) {

                super.updateItem(user, bln);

                setGraphic(null);
                setText(null);
                Circle circle = new Circle();


                if (user != null) {
                    HBox hBox = new HBox();

                    Text name = new Text(user.getFirstName() + " " + user.getLastName());

                    ImageView statusImageView = new ImageView();
                    System.out.println(user.getMode().toString().toLowerCase());
                    String path = "/static/images/mode/" + user.getMode().toString().toLowerCase() + ".png";
                    System.out.println(path);
                    Image statusImage = new Image(getClass()
//                            .getResource("/static/images/mode/available.png").toString(), 16, 16, true, true);
                            .getResource("/static/images/mode/" + user.getMode().toString().toLowerCase().trim() + ".png").toString(), 36, 36, true, true);
                    statusImageView.setImage(statusImage);
                    Image image = renderImage.convertToImage(user.getImage());
                    if (image == null) {
                        image = new Image(getClass().getResource("/static/images/Smile.png").toString(), 40, 40, true, false);
                    }
                    circle.setCenterX(40);
                    circle.setRadius(40);

                    StackPane stackPane = new StackPane();

                    stackPane.setMargin(statusImageView, new Insets(65, 0, 0, 45));

                    circle.setFill(new ImagePattern(image));
                    stackPane.getChildren().addAll(circle, statusImageView);
                    hBox.setSpacing(5);


                    hBox.setAlignment(Pos.CENTER_LEFT);
                    InviteFriend = new Button("Add to group!");
                    //setStyleForButtons

                    InviteFriend.setStyle("-fx-background-color: #0078D4; -fx-background-radius: 20; -fx-text-fill: #ffffff; -fx-font-size: 12px; -fx-alignment: CENTER;");
                    InviteFriend.setAlignment(Pos.CENTER_RIGHT);
                    hBox.getChildren().addAll(stackPane, name, InviteFriend);
                    handleViewButton(InviteFriend, currentChatGroup, user);
                    setPrefHeight(80);
                    setGraphic(hBox);
                }

            }
        };
        return cell;

    }

    public boolean handleViewButton(Button button, ChatGroup chatGroup, User user) {
        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                HomeControllerImpl homeController = new HomeControllerImpl();
                homeController.addFriend(chatGroup, user);
                button.setVisible(false);
            }

        });

        return true;
    }

    public void setCurrentChatGroup(ChatGroup currentChatGroup) {
        this.currentChatGroup = currentChatGroup;
    }

    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
        System.out.println("USer in chat renderer invite friends" + currentUser.getFirstName());
    }
}
