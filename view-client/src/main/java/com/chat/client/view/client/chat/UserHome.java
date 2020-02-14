package com.chat.client.view.client.chat;

import com.chat.client.controller.client.chatGroup.ChatGroupController;
import com.chat.client.controller.client.pushNotifications.PushNotificationController;
import com.chat.client.controller.client.pushNotifications.PushNotificationInterface;
import com.chat.client.network.client.user.UserHandler;
import com.chat.client.network.client.user.impl.UserHandlerImpl;
import com.chat.server.model.chat.Notification;
import com.chat.client.view.client.user.UserProfileController;
import com.chat.server.model.chat.ChatGroup;
import com.chat.server.model.user.User;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class UserHome implements Initializable, PushNotificationInterface {

    @FXML
    private ListView userList;
    @FXML
    private AnchorPane containerPane;
    ListProperty<User> myFriendsListProperty = new SimpleListProperty<>();
    private ObservableList<User> myFriendsList = FXCollections.observableArrayList();
    //app controller
    private ChatGroupController chatGroupInterface;
    private PushNotificationController pushNotificationController;
    private User currrentUser;

    private Stage stage;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        setListView();
    }

    public UserHome() {
        try {
            chatGroupInterface = new ChatGroupController();
            pushNotificationController = new PushNotificationController();
            pushNotificationController.setPushNotifications(this);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public void nav(MouseEvent mouseEvent) {
    }

    public void logOut(MouseEvent mouseEvent) {
    }

    public void nav1(MouseEvent mouseEvent) {
    }

    private void setListView() {
        //@shaheen
        //todo change the list user friends
        //all user for testing the list view
        UserHandler userHandler = new UserHandlerImpl();
        List<User> users = userHandler.getAllUsers();
        myFriendsList = FXCollections.observableList(users);
        System.out.println(users.size());
        userList.setItems(myFriendsList);
        userList.setCellFactory(new CellRenderer());
    }

    @FXML
    private void onFriendsListClicked(MouseEvent mouseEvent) {
        User user = (User) userList.getSelectionModel().getSelectedItem();
        if (user != null) {
            loadFriendProfile(user);
        }
    }

    private void loadFriendProfile(User user) {
        try {
            FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/templates/user/User_profile.fxml"));
            Parent root = loader.load();
            UserProfileController userProfileController = loader.getController();
            userProfileController.setUser(user);
            System.out.println("my Profile is loaded ............");
            containerPane.getChildren().setAll(root);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setCurrrentUser(User currrentUser) {
        this.currrentUser = currrentUser;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    private void loadChatGroup(ChatGroup chatGroup) {

        try {
            FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/templates/chat/chat-view.fxml"));
            Parent root = loader.load();
            //view controller
            ChatViewController chatViewController = (ChatViewController) loader.getController();
            chatViewController.setUser(currrentUser);
            //@yasmine
            //todo don't forget to add groupchat to chatviewcontroller
            //---------
            //app controller
            ChatGroupController chatGroupController = new ChatGroupController();
            //add ref to each other
            chatGroupController.setChatGroupInterface(chatViewController);
            chatViewController.setChatGroupInterface(chatGroupController);

//                containerPane.getChildren().add(root);

                AnchorPane child = new AnchorPane(root);
                AnchorPane.setTopAnchor(child, 10.0);
                AnchorPane.setBottomAnchor(child, 10.0);
                AnchorPane.setLeftAnchor(child, 10.0);
                AnchorPane.setRightAnchor(child, 10.0);
                containerPane.getChildren().setAll((AnchorPane) child);


//                containerPane = new AnchorPane(root);
//                content = (AnchorPane) FXMLLoader.load("vista2.fxml");

                System.out.println(user);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void setCurrrentUser(User currrentUser) {
        this.currrentUser = currrentUser;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @Override
    public void receiveNotification(Notification notification) {
        System.out.println(notification);
    }
}
