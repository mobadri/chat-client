package com.chat.client.view.client.friend;

import com.chat.client.view.client.chat.render.ChatRendererwithbuttons;
import com.chat.client.view.client.user.UserViewHome;
import com.chat.server.model.user.FriendStatus;
import com.chat.server.model.user.User;
import com.chat.server.model.user.UserFriend;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class AddFriend implements Initializable {
    private ObservableList<User> allUsers = FXCollections.observableArrayList();
    String phone;
    @FXML
    private TextField phoneNumberSearch;
    @FXML
    private ListView usersListView;
    private User currentUser;
    private UserViewHome homeController;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        search();
        usersListView.refresh();
    }

    private void setListView() {

        //all user for testing the list view
        System.out.println("homeController" + homeController);
        List<User> users = homeController.findByPhone(phone);

        System.out.println(users.size());
        boolean remove = users.removeIf(user -> user.getPhone().equals(currentUser.getPhone()));
        allUsers = FXCollections.observableList(users);
        usersListView.setItems(allUsers);
        ChatRendererwithbuttons chatRendererwithbuttons = new ChatRendererwithbuttons();
        chatRendererwithbuttons.setCurrentUser(currentUser);
//        chatRendererwithbuttons.setFriends(currentUser.getFriends());
        chatRendererwithbuttons.setAddFriendController(this);
        usersListView.setCellFactory(chatRendererwithbuttons);

    }

    @FXML
    private void search() {
        phoneNumberSearch.textProperty().addListener((observableValue, oldValue, newValue) -> {
            if ((newValue.isEmpty() || newValue == null)) {
                usersListView.setItems(null);
            } else {
                phone = newValue;
                setListView();
            }
        });
    }


    public UserFriend getStatus(User currentUser, User friend) {
        return homeController.getFriendStatus(currentUser, friend);

    }

    public void removeFriend(User user, User userFriend) {
        homeController.removeFriend(user, userFriend);
        setListView();

    }

    public void addFriend(User user, User friend) {
        homeController.addFriend(user, friend);
        setListView();
    }

    public void updateFriend(User currentUser, User friend, FriendStatus status) {
        homeController.updateFriend(currentUser, friend, status);
        setListView();
    }

    //----------------------------------------------------------------------
    //-------------------setter section ------------------------------------
    //----------------------------------------------------------------------
    public void setHomeController(UserViewHome homeController) {
        this.homeController = homeController;
    }

    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }

}