package com.chat.client.view.client.user;

import com.chat.client.view.client.chat.CellRenderer;
import com.chat.client.view.client.friend.AddFriend;
import com.chat.server.model.user.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class UserFriends implements Initializable {

    @FXML
    private ListView userList;
    private ObservableList<User> friendsObservableList = FXCollections.observableArrayList();
    private User currentUser;
    private UserViewHome userViewHome;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        new Thread(() -> setFriendsListView(currentUser.getFriends())).start();
    }

    @FXML
    private void onFriendsListClicked(MouseEvent mouseEvent) {

        User user = (User) userList.getSelectionModel().getSelectedItem();
        if (user != null) {
            userViewHome.loadUserProfile(user);
        }
    }

    @FXML
    public void addFriend(MouseEvent mouseEvent) {

        try {

            FXMLLoader loader =
                    new FXMLLoader(getClass().getResource("/templates/friend/addFriend.fxml"));
            Parent root = loader.load();
            AddFriend controller = loader.getController();
            controller.setCurrentUser(currentUser);
            Stage friendStage = new Stage();
            friendStage.setScene(new Scene(root));
            friendStage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void setFriendsListView(List<User> users) {

        friendsObservableList = FXCollections.observableList(users);
        userList.setItems(friendsObservableList);
        userList.setCellFactory(new CellRenderer());
    }

    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }

    public void setUserViewHome(UserViewHome userViewHome) {
        this.userViewHome = userViewHome;
    }
}