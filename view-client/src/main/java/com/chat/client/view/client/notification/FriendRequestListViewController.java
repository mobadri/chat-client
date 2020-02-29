package com.chat.client.view.client.notification;

import com.chat.server.model.user.User;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;

import java.net.URL;
import java.util.ResourceBundle;

public class FriendRequestListViewController implements Initializable {

    @FXML
    private ListView friendRequestListView;
    FriendRequestCellRender cellRender;

    private ObservableList<User> friendRequestList = FXCollections.observableArrayList();
    private ListProperty<User> friendRequestProperty = new SimpleListProperty<>();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        friendRequestProperty.set(friendRequestList);
        friendRequestListView.itemsProperty().bindBidirectional(friendRequestProperty);
        friendRequestListView.setItems(friendRequestProperty);
        friendRequestListView.setCellFactory(cellRender);
        friendRequestListView.scrollTo(friendRequestListView.getItems().size());
    }

    public FriendRequestListViewController() {
        cellRender = new FriendRequestCellRender();
        cellRender.setController(this);
    }

    public void addFriendRequestequest(User user) {
        friendRequestList.add(user);
    }

    public void removeFriendRequestFromUI(User user) {
        friendRequestList.remove(user);
    }
}
