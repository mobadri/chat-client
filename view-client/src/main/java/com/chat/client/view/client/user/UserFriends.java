package com.chat.client.view.client.user;

import com.chat.client.view.client.chat.render.CellRenderer;
import com.chat.client.view.client.friend.AddFriend;
import com.chat.server.model.user.User;
import com.jfoenix.controls.JFXTextField;
import javafx.application.Platform;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class UserFriends implements Initializable {

    @FXML
    private ListView userList;
    @FXML
    private JFXTextField searchForFriends;

    private ObservableList<User> friendsObservableList = FXCollections.observableArrayList();
    private ListProperty<User> friendsProp = new SimpleListProperty<>();
    private User currentUser;
    private UserViewHome userViewHome;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setSearchForFriends();
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
            controller.setHomeController(userViewHome);
            controller.setUserFriends(this);
            Stage friendStage = new Stage();
            Scene scene = new Scene(root);
            scene.setFill(Color.TRANSPARENT);
            friendStage.setScene(scene);
            friendStage.initStyle(StageStyle.TRANSPARENT);
            friendStage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void setFriendsListView(List<User> users) {
        friendsObservableList = FXCollections.observableList(users);
        friendsProp.setValue(friendsObservableList);
        userList.itemsProperty().bindBidirectional(friendsProp);
        userList.setItems(friendsProp);
        userList.setCellFactory(new CellRenderer());

    }

    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
        Platform.runLater(() -> setFriendsListView(currentUser.getFriends()));
    }

    public void setUserViewHome(UserViewHome userViewHome) {
        this.userViewHome = userViewHome;
    }

    public void setFriendMode(User friend) {

        List<User> users = userList.getItems();
        boolean isRemoved = users.removeIf((user) -> user.getId() == friend.getId());
        if (isRemoved) {
            users.add(friend);
            userList.setItems(FXCollections.observableArrayList(users));
        }
    }

    private void searchTextListener(FilteredList<User> filteredData) {

        searchForFriends.textProperty().addListener((observable, oldValue, newValue) ->
                filteredData.setPredicate(friend -> {
                    if (newValue == null || newValue.isEmpty()) {
                        return true;
                    }
                    String lowerCaseFilter = newValue.toLowerCase();
                    if (friend.getFirstName().toLowerCase().contains(lowerCaseFilter)
                            || friend.getPhone().contains(lowerCaseFilter)
                            || (friend.getCountry() != null
                            && friend.getCountry().toLowerCase().contains(lowerCaseFilter))
                            || (friend.getLastName() != null
                            && friend.getLastName().toLowerCase().contains(lowerCaseFilter))) {
                        System.out.println("Search is here");
                        return true;
                    }
                    return false;
                }));
    }

    private void setSearchForFriends() {
        FilteredList<User> filteredData = new FilteredList<>(friendsObservableList, p -> true);
        searchTextListener(filteredData);
        SortedList<User> sortedData = new SortedList<>(filteredData);
        userList.setItems(sortedData);
    }

    public void addUserTolist(User user) {
        friendsProp.add(user);
    }
}