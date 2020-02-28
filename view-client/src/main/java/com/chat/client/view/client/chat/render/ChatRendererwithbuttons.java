package com.chat.client.view.client.chat.render;

import com.chat.client.view.client.friend.AddFriend;
import com.chat.server.model.user.FriendStatus;
import com.chat.server.model.user.User;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.util.Callback;

import java.util.List;

public class ChatRendererwithbuttons implements Callback<ListView<User>, ListCell<User>> {
    List<User> friends;
    User currentUser;
    private AddFriend addFriend;


    @Override
    public ListCell<User> call(ListView<User> p) {

        ListCell<User> cell = new ListCell<User>() {

            @Override
            protected void updateItem(User user, boolean bln) {
                //   p.refresh();
                super.updateItem(user, bln);
                setGraphic(null);
                setText(null);
                if (user != null) {
                    HBox hBox = new HBox();
                    Text name = new Text(user.getFirstName() + " " + user.getLastName());

                    ImageView statusImageView = new ImageView();
//                    System.out.println(user.getMode().toString().toLowerCase());
//                    String path = "/static/images/mode/" + user.getMode().toString().toLowerCase() + ".png";
//                    System.out.println(path);


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
                    FriendStatus status = addFriend.getStatus(currentUser, user);

                    if (status == null) {
                        buttonaddFriend.setText("Add Friend");
                    } else {
                        switch (status) {
                            case PENDING:
                                buttonaddFriend.setText("Cancel Request");
                                break;
                            case REJECT:
                                buttonaddFriend.setText("Add Friend");
                                break;
                            case APPROVED:
                                buttonaddFriend.setText("Remove Friend");
                                break;
                            case REQUEST:
                                buttonaddFriend.setText("Accept Friend");
                            default:
                                buttonaddFriend.setText("ADD Friend");
                        }
                    }

                    setButtonAction(buttonaddFriend, status, user);

                    buttonaddFriend.setStyle("-fx-background-color: #0078D4; -fx-background-radius: 20; -fx-text-fill: #ffffff; -fx-font-size: 12px; -fx-alignment: CENTER;");
                    hboxButtons.getChildren().addAll(buttonaddFriend, buttonviewProfile);

                    vbox.getChildren().addAll(hBox, hboxButtons);

                    setGraphic(vbox);
                }
            }

        };
        return cell;
    }


    private FriendStatus setButtonAction(Button button, FriendStatus status, User userFriend) {
        FriendStatus currentStatus = status;
        System.err.println("handel button action");
        button.setOnAction((event) -> {
            if (status != null) {
                switch (status) {
                    case PENDING:
                        cancelRequest(currentUser, userFriend);
                        button.setText("Add Friend");
                        break;
                    case APPROVED:
                        removeFriend(currentUser, userFriend);
                        button.setText("Add Friend");
                        break;
                    case REJECT:
                        addFriend(currentUser, userFriend);
                        button.setText("Add Friend");
                    case REQUEST:
                        approveRequest(currentUser, userFriend);
                        button.setText("Remove Friend");
                }
            } else {
                addFriend(currentUser, userFriend);
                button.setText("Cancel Request");
            }
        });

        return currentStatus;
    }


    private void cancelRequest(User currentUser, User userFriend) {
        System.err.println("cancelRequest");
        addFriend.removeFriend(currentUser, userFriend);

    }

    private void removeFriend(User currentUser, User friend) {
        addFriend.removeFriend(currentUser, friend);
    }

    private void addFriend(User currentUser, User friend) {
        System.err.println("add Friend clicked");
        addFriend.addFriend(currentUser, friend);
    }

    private void approveRequest(User currentUser, User userFriend) {
        addFriend.updateFriend(currentUser, userFriend, FriendStatus.APPROVED);
    }

    //    Boolean isFriend(User user) {
//        return
//                friends.parallelStream().anyMatch(user1 -> {
//                    return user1.getId() == user.getId() ? true : false;
//                });
//    }
//
//    void showAlert() {
//        Alert alert = new Alert(Alert.AlertType.ERROR, "Delete " + " ?", ButtonType.YES, ButtonType.NO, ButtonType.CANCEL);
//        alert.showAndWait();
//
//        if (alert.getResult() == ButtonType.YES) {
//            homeController.removeFriend(currentUser.getId(), userFriend.getId());
//
//        }
//    }
    public void setCurrentUser(User currentUser) {

        this.currentUser = currentUser;
    }

    public void setFriends(List<User> friends) {
        this.friends = friends;

    }

    public void setAddFriend(AddFriend addFriend) {
        this.addFriend = addFriend;
    }
}
