package com.chat.client.view.client.chat;

import com.chat.client.controller.client.user.HomeController;
import com.chat.client.view.client.chat.render.ChatRendererInviteFriend;
import com.chat.server.model.chat.ChatGroup;
import com.chat.server.model.user.User;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class AddFriendToChatGroup implements Initializable {
    private ObservableList<User> allUsers = FXCollections.observableArrayList();
    ChatGroup chatGroup;

    @FXML
    private TextField searchfriendstoadd;

    @FXML
    private ListView listviewtoaddfriends;


    public void setGroupChat(ChatGroup chatGroup) {
        this.chatGroup = chatGroup;
    }

    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }

    User currentUser;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Search();
    }

    private void setListView(String phone) {

        //all user for testing the list view
        HomeController homeController = new HomeController();
        List<User> users = homeController.findByPhone(phone);
        boolean checkifDeleted;
        checkifDeleted = users.removeIf(user -> user.getPhone().equals(currentUser.getPhone()));

        List<User> friends = currentUser.getFriends();
        List<User> usersInGroup = chatGroup.getUsers();
        List<User> usersList = new ArrayList<>();
        for (User user : friends) {

            if (!checkIfInGroupUser(user)) {
                usersList.add(user);

            }

        }
        System.out.println("User in List To print " + usersList.size());

        // friends.parallelStream().filter(user -> user.getId()!=usersInGroup.forEach(user1 -> ); )
        System.out.println("User is = " + friends.size());
        System.out.println("User is after Remove = " + users.size());
        System.out.println("Check if deleted" + checkifDeleted);
        allUsers = FXCollections.observableList(usersList);
        System.out.println(users.size());
        ChatRendererInviteFriend chatRendererInviteFriend = new ChatRendererInviteFriend();
        chatRendererInviteFriend.setCurrentUser(currentUser);

        listviewtoaddfriends.setItems(allUsers);
        listviewtoaddfriends.setCellFactory(new ChatRendererInviteFriend());

    }

    private void Search() {
        searchfriendstoadd.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String oldValue, String newValue) {
                if ((newValue.isEmpty() || newValue == null)) {
                    listviewtoaddfriends.setItems(null);
                } else {
                    setListView(newValue);
                }
            }
        });

    }

    boolean checkIfInGroupUser(User user) {

        for (User user1 : chatGroup.getUsers()) {
            if (user1.getId() == user.getId()) {

                return true;
            }

        }
        return false;
    }


}
