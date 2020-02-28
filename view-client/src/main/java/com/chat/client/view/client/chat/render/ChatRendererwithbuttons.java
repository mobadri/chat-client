package com.chat.client.view.client.chat.render;

import com.chat.client.controller.client.user.HomeController;
import com.chat.server.model.user.FriendStatus;
import com.chat.server.model.user.User;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.util.Callback;

import java.util.List;

public class ChatRendererwithbuttons implements Callback<ListView<User>, ListCell<User>> {
    List<User> friends;
    HomeController homeController;
    User currentUser;
    User userFriend;

    public void setHomeController(HomeController homeController) {
        this.homeController = homeController;
        System.out.println("Home Controller in chat render" + homeController);
    }

    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }

    public void setFriends(List<User> friends) {
        this.friends = friends;

    }

    @Override
    public ListCell<User> call(ListView<User> p) {

        ListCell<User> cell = new ListCell<User>() {

            @Override
            protected void updateItem(User user, boolean bln) {
                //   p.refresh();
                super.updateItem(user, bln);
                setGraphic(null);
                setText(null);
                userFriend = user;
                if (user != null) {
                    HBox hBox = new HBox();

                    Text name = new Text(user.getFirstName() + " " + user.getLastName());

                    ImageView statusImageView = new ImageView();
                    System.out.println(user.getMode().toString().toLowerCase());
                    String path = "/static/images/mode/" + user.getMode().toString().toLowerCase() + ".png";
                    System.out.println(path);


                    ImageView pictureImageView = new ImageView();
//                    Image image = new Image(getClass().getResource("/images/" + user.getPicture().toLowerCase() + ".png").toString(), 50, 50, true, true);
                    Image image = new Image(getClass().getResource("/static/images/Smile.png").toString(), 50, 50, true, true);
                    pictureImageView.setImage(image);
                    hBox.setSpacing(5);
                    hBox.getChildren().addAll(statusImageView, pictureImageView, name);
                    hBox.setAlignment(Pos.CENTER_LEFT);
                    VBox vbox = new VBox();
                    Button buttonviewProfile = new Button("View Profile");
                    //setStyleForButtons
                    Button buttonaddFriend = new Button("ADD Friend");

                    buttonviewProfile.setStyle("-fx-background-color: #0078D4; -fx-background-radius: 20; -fx-text-fill: #ffffff; -fx-font-size: 12px; -fx-alignment: CENTER;");
                    HBox hboxButtons = new HBox();
                    hboxButtons.setSpacing(5);
                    hboxButtons.setAlignment(Pos.CENTER_RIGHT);
                    // check if not firend
                    // button on add
                    FriendStatus satatus = homeController.getSatatus(currentUser.getId(), user.getId());
                    System.out.println("Status in chat renderer " + satatus);
                    System.out.println("Status in chat renderer " + user.getFirstName());

                    if (satatus == null) {
                        buttonaddFriend.setText("ADD Friend");
                    } else {
                        switch (satatus) {
                            case PENDING:
                                buttonaddFriend.setText("Request Friend");
                                break;
                            case REJECT:
                                buttonaddFriend.setText("Rejected");
                                break;
                            case APPROVED:
                                buttonaddFriend.setText("Remove Friend");

                                break;
                            default:
                                buttonaddFriend.setText("ADD Friend");

                        }
                    }
                    // check if friend
                    // button on remove

                    if (isFriend(user)) {
                        buttonaddFriend.setOnAction(new EventHandler<ActionEvent>() {
                            @Override
                            public void handle(ActionEvent actionEvent) {
                                int count = homeController.removeFriend(currentUser.getId(), user.getId());
                                System.out.println("Count is  = " + count + " Current user is = " + currentUser.getFirstName() + " The Friend is " + user.getId());
                                getListView().refresh();
                            }
                        });
                    } else {
                        if (satatus == null || satatus.ordinal() == 2) {
                            buttonaddFriend.setOnAction(new EventHandler<ActionEvent>() {
                                @Override
                                public void handle(ActionEvent actionEvent) {
                                    int count = homeController.addFriend(currentUser, user);
                                    System.out.println("Count Add Friend button" + count);
                                    getListView().refresh();
                                }
                            });
                        } else {
                            buttonaddFriend.setOnAction(new EventHandler<ActionEvent>() {
                                @Override
                                public void handle(ActionEvent actionEvent) {
                                    System.out.println("I am In Button addedFriend");
                                    int i = homeController.updateFriend(user.getId(), currentUser.getId(), FriendStatus.APPROVED);

                                    if (i == 0) {
                                        showAlert();
                                    } else {
                                        buttonaddFriend.setText("Friend Added");
                                    }
                                    System.out.println("Rows Updated" + i);


                                }
                            });
                        }


                    }


                    // check if pending
                    // button accept
                    buttonaddFriend.setStyle("-fx-background-color: #0078D4; -fx-background-radius: 20; -fx-text-fill: #ffffff; -fx-font-size: 12px; -fx-alignment: CENTER;");
                    hboxButtons.getChildren().addAll(buttonaddFriend, buttonviewProfile);

                    vbox.getChildren().addAll(hBox, hboxButtons);
                    setGraphic(vbox);
                }

            }
        };
        return cell;

    }

    Boolean isFriend(User user) {
        return
                friends.parallelStream().anyMatch(user1 -> {
                    return
                            user1.getId() == user.getId() ? true : false;
                });
    }

    void showAlert() {
        Alert alert = new Alert(Alert.AlertType.ERROR, "Delete " + " ?", ButtonType.YES, ButtonType.NO, ButtonType.CANCEL);
        alert.showAndWait();

        if (alert.getResult() == ButtonType.YES) {
            homeController.removeFriend(currentUser.getId(), userFriend.getId());

        }
    }

}
