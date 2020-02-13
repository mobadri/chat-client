package com.chat.client.view.client.chat;

import com.chat.client.controller.client.chatGroup.ChatGroupController;
import com.chat.client.network.client.user.UserHandler;
import com.chat.client.network.client.user.impl.UserHandlerImpl;
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

public class UserHome implements Initializable {

    @FXML
    private ListView userList;
    @FXML
    private AnchorPane containerPane;
    ListProperty<User> myFriendsListProperty = new SimpleListProperty<>();
    private ObservableList<User> myFriendsList = FXCollections.observableArrayList();

    private User currrentUser;

    private Stage stage;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        setListView();
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
            try {
                FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/templates/chat/chat-view.fxml"));
                Parent root = loader.load();
                ChatViewController chatViewController = (ChatViewController) loader.getController();
                chatViewController.setUser(user);
                //@yasmine
                //todo don't forget to add groupchat to chatviewcontroller
                //---------
                ChatGroupController chatGroupController = new ChatGroupController();
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
}
