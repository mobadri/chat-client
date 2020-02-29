package com.chat.client.view.client.chat.render;

import com.chat.client.view.client.friend.AddFriend;
import com.chat.server.model.user.FriendStatus;
import com.chat.server.model.user.User;
import com.chat.server.model.user.UserFriend;
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

public class ChatRendererwithbuttons implements Callback<ListView<User>, ListCell<User>> {
    User currentUser;
    private final String addFriend = "Add Friend";
    private final String undoReject = "Undo Reject";
    private final String resendRequest = "Resend Request";
    private final String cancelRequest = "Cancel Request";
    private final String acceptRequest = "Accept Request";
    private final String rejectRequest = "Reject Request";
    private final String removeFriend = "Remove Friend";
    private AddFriend addFriendController;

    @Override
    public ListCell<User> call(ListView<User> p) {

        ListCell<User> cell = new ListCell<User>() {

            @Override
            protected void updateItem(User user, boolean bln) {
                super.updateItem(user, bln);
                setGraphic(null);
                setText(null);
                if (user != null) {
                    HBox hBox = new HBox();
                    Text name = new Text(user.getFirstName() + " " + user.getLastName());

                    ImageView statusImageView = new ImageView();
                    ImageView pictureImageView = new ImageView();
                    Image image = new Image(getClass().getResource("/static/images/Smile.png").toString(), 50, 50, true, true);
                    pictureImageView.setImage(image);
                    hBox.setSpacing(5);
                    hBox.getChildren().addAll(statusImageView, pictureImageView, name);
                    hBox.setAlignment(Pos.CENTER_LEFT);
                    VBox vbox = new VBox();
                    Button buttonViewProfile = new Button("View Profile");
                    //setStyleForButtons
                    Button buttonAddFriend = new Button(addFriend);
                    Button buttonReject = new Button(rejectRequest);

                    buttonViewProfile.setStyle("-fx-background-color: #354578; -fx-background-radius: 20; -fx-text-fill: #ffffff; -fx-font-size: 12px; -fx-alignment: CENTER;");
                    buttonReject.setStyle("-fx-background-color: red; -fx-background-radius: 20; -fx-text-fill: #ffffff; -fx-font-size: 12px; -fx-alignment: CENTER;");
                    HBox hboxButtons = new HBox();
                    hboxButtons.setSpacing(5);
                    hboxButtons.setAlignment(Pos.CENTER_RIGHT);
                    // check if not firend
                    // button on add
                    UserFriend userFriend = addFriendController.getStatus(currentUser, user);
                    FriendStatus status = null;
                    // i am his friend Or he is my friend

                    if (userFriend.getFriendStatus() == null) {
                        buttonAddFriend.setText(addFriend);
                    } else {
                        status = userFriend.getFriendStatus();
                        switch (status) {
                            case PENDING:
                                if (userFriend.getUser() == currentUser.getId()) {
                                    buttonAddFriend.setText(cancelRequest);
                                } else {
                                    buttonAddFriend.setText(acceptRequest);
                                    hboxButtons.getChildren().add(buttonReject);
                                    setRejectButtonAction(buttonReject, status, user);
                                }
                                break;
                            case REJECT:
                                if (currentUser.getId() == userFriend.getUser()) {
                                    buttonAddFriend.setText(resendRequest);
                                } else {
                                    buttonAddFriend.setText(undoReject);
                                }
                                break;
                            case APPROVED:
                                buttonAddFriend.setText(removeFriend);

                                break;
                            default:
                                buttonAddFriend.setText(addFriend);
                        }
                    }
                    setButtonAction(buttonAddFriend, status, user, userFriend);
                    buttonAddFriend.setStyle("-fx-background-color: #354578; -fx-background-radius: 20; -fx-text-fill: #ffffff; -fx-font-size: 12px; -fx-alignment: CENTER;");
                    hboxButtons.getChildren().addAll(buttonAddFriend, buttonViewProfile);

                    vbox.getChildren().addAll(hBox, hboxButtons);

                    setGraphic(vbox);
                }
            }

        };
        return cell;
    }

    private void setButtonAction(Button button, FriendStatus status, User friend, UserFriend userFriend) {
        button.setOnAction((event) -> {
            if (status != null) {
                switch (status) {
                    case PENDING:
                        // i send a request before
                        if (currentUser.getId() == userFriend.getUser()) {
                            cancelRequest(currentUser, friend);
                            button.setText(addFriend);
                        } else {
                            // i have a friend request
                            System.err.println("approveRequest");
                            approveRequest(friend, currentUser);
                            button.setText(removeFriend);
                        }
                        break;
                    case APPROVED:
                        if (currentUser.getId() == userFriend.getUser()) {
                            removeFriend(currentUser, friend);
                        } else {
                            removeFriend(friend, currentUser);
                        }
                        button.setText(addFriend);
                        break;
                    case REJECT:
                        // i have reject a request
                        if (currentUser.getId() == userFriend.getUser()) {
                            addFriend(currentUser, friend);
                        } else {
                            // friend reject my request
                            addFriend(friend, currentUser);
                        }
                        button.setText(addFriend);
                        break;
                }
            } else {
                addFriend(currentUser, friend);
                button.setText(cancelRequest);
            }
        });

    }

    private void cancelRequest(User currentUser, User userFriend) {
        System.err.println("cancelRequest");
        addFriendController.removeFriend(currentUser, userFriend);

    }

    private void removeFriend(User currentUser, User friend) {
        addFriendController.removeFriend(currentUser, friend);
    }

    private void addFriend(User currentUser, User friend) {
        System.err.println("add Friend clicked");
        UserFriend status = addFriendController.getStatus(currentUser, friend);
        if (status.getFriendStatus() != null) {
            addFriendController.updateFriend(currentUser, friend, FriendStatus.PENDING);
        } else {
            addFriendController.addFriend(currentUser, friend);
        }
    }

    private void approveRequest(User currentUser, User userFriend) {
        addFriendController.updateFriend(currentUser, userFriend, FriendStatus.APPROVED);
    }

    private void setRejectButtonAction(Button button, FriendStatus status, User userFriend) {
        button.setOnAction((actionEvent -> {
            System.err.println("reject");
            addFriendController.updateFriend(userFriend, currentUser, FriendStatus.REJECT);

        }
        ));
    }

    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }

    public void setAddFriendController(AddFriend addFriendController) {
        this.addFriendController = addFriendController;
    }
}
