package com.chat.client.view.client.chat;

import com.chat.client.controller.client.user.HomeController;
import com.chat.client.view.client.friend.AddFriend;
import com.chat.server.model.chat.Notification;
import com.chat.server.model.user.User;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
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
    static String friendAdded ="Add Friend";
    static String friendRemoved ="Remove Friend";
    Button buttonaddFriend,removeFriend ;
    HomeController homeController = new HomeController();
    User currentUser;

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
                super.updateItem(user, bln);
                setGraphic(null);
                setText(null);
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

                    buttonviewProfile.setStyle(  "-fx-background-color: #0078D4; -fx-background-radius: 20; -fx-text-fill: #ffffff; -fx-font-size: 12px; -fx-alignment: CENTER;");
                    HBox hboxButtons = new HBox();
                    hboxButtons.setSpacing(5);
                    hboxButtons.setAlignment(Pos.CENTER_RIGHT);


                    if(isFriend(user))
                    {
                        removeFriend = new Button(friendRemoved);
                        removeFriend.setStyle(  "-fx-background-color: #FF0000; -fx-background-radius: 20; -fx-text-fill: #ffffff; -fx-font-size: 12px; -fx-alignment: CENTER;");
                        System.out.println("User is = " + user.getId());
                        handleAddOrRemoveButton(user,removeFriend);
                        hboxButtons.getChildren().addAll(removeFriend,buttonviewProfile);
                    }
                    else
                    {
                        buttonaddFriend = new Button(friendAdded);
                        buttonaddFriend.setStyle(  "-fx-background-color: #0078D4; -fx-background-radius: 20; -fx-text-fill: #ffffff; -fx-font-size: 12px; -fx-alignment: CENTER;");
                        hboxButtons.getChildren().addAll(buttonaddFriend,buttonviewProfile);
                       handleAddOrRemoveButton(user,buttonaddFriend);
                    }



                    vbox.getChildren().addAll(hBox,hboxButtons);
                    setGraphic(vbox);
                }
            }
        };
        return cell;
    }
    Boolean isFriend(User user )
    {
        return
        friends.parallelStream().anyMatch(user1 -> {
            return
            user1.getId()==user.getId()?true:false;
        });
    }

    public void handleAddOrRemoveButton(User user, Button button){

        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                if(button.getText().equals(friendRemoved))
                {
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            String friendRemoved = "Friend Removed";
                            int count = homeController.removeFriend(currentUser.getId(), user.getId());
                            if (count > 0) {
                                System.out.println("This is Number of Friend Deleted" + count);
                                button.setText(friendRemoved);
                            }
                        }
                    });

                }
                else if(button.getText().equals(friendAdded))
                {
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                           int numberofRows = homeController.addFriend(currentUser,user);
                           if(numberofRows>0) {
                               System.out.println("This is Number of Friend added" + numberofRows);
                               String freindRequest = "Friend Request";

                               button.setText(freindRequest);
                           }
                            System.out.println("" + currentUser.getFirstName() + user.getFirstName());
                        }
                    });

                }

            }
        });
    }


}


