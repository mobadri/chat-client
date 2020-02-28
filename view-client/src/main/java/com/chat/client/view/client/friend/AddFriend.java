package com.chat.client.view.client.friend;

import com.chat.client.controller.client.user.HomeController;
import com.chat.client.view.client.chat.render.ChatRendererwithbuttons;
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
import java.util.List;
import java.util.ResourceBundle;

public class AddFriend implements Initializable {
    private ObservableList<User> allUsers = FXCollections.observableArrayList();
    HomeController homeController;
    @FXML
    private TextField phoneNumberSearch;
    @FXML
    private ListView usersListView;
    private User currentUser;

    public void setHomeController(HomeController homeController) {
        this.homeController = homeController;
    }

    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        Search();
        usersListView.refresh();
        //setListView(phoneNumberSearch.getText());
        // setSearchToAddNewFriends();


    }

    private void setListView(String phone) {

        //all user for testing the list view
        HomeController homeController = new HomeController();
        List<User> users = homeController.findByPhone(phone);
        System.out.println("User is = " + users.size());
        boolean remove = users.removeIf(user -> user.getPhone().equals(currentUser.getPhone()));
        System.err.println("REMOVED " + remove);
        allUsers = FXCollections.observableList(users);
        System.out.println(users.size());

        usersListView.setItems(allUsers);
        ChatRendererwithbuttons chatRendererwithbuttons = new ChatRendererwithbuttons();
        chatRendererwithbuttons.setCurrentUser(currentUser);
        System.out.println("Home Controller in add friend is = " + homeController);
        chatRendererwithbuttons.setHomeController(homeController);
        chatRendererwithbuttons.setFriends(currentUser.getFriends());
        usersListView.setCellFactory(chatRendererwithbuttons);

    }

    private void Search() {
        phoneNumberSearch.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String oldValue, String newValue) {
                if ((newValue.isEmpty() || newValue == null)) {
                    usersListView.setItems(null);
                } else {
                    setListView(newValue);
                }
            }
        });

    }


}