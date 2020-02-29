package com.chat.client.view.client.notification;

import com.chat.client.view.client.user.UserViewHome;
import com.chat.server.model.chat.Notification;
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

public class NotificationViewListController implements Initializable {

    @FXML
    private ListView notificationListView;
    private NotificationCellRenderer cellRenderer;
    private UserViewHome userHome;

    private ObservableList<Notification> myNotificationList = FXCollections.observableArrayList();

    private ListProperty<Notification> myNotificationProperty = new SimpleListProperty<>();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        myNotificationProperty.set(myNotificationList);
        notificationListView.itemsProperty().bindBidirectional(myNotificationProperty);
        notificationListView.setItems(myNotificationProperty);
        notificationListView.setCellFactory(cellRenderer);
        notificationListView.scrollTo(notificationListView.getItems().size());
    }


    public NotificationViewListController() {
        cellRenderer = new NotificationCellRenderer();
        cellRenderer.setController(this);
    }

    public void addNotification(Notification notification) {

        myNotificationList.add(notification);
    }

    public void removeNotificationFromUI(Notification notification) {

        myNotificationList.remove(notification);
    }

    public void clientAcceptFile(String fileName, int i, User userTo) {
        userHome.clientAcceptFile(fileName, i, userTo);
    }

    public void setUserHome(UserViewHome userHome) {
        this.userHome = userHome;
    }
}
