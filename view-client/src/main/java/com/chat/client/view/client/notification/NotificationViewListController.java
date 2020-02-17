package com.chat.client.view.client.notification;

import com.chat.client.view.client.chat.ChatGroupCellRenderer;
import com.chat.server.model.chat.ChatGroup;
import com.chat.server.model.chat.Notification;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class NotificationViewListController implements Initializable {

    @FXML
    private ListView notificatioList;

    List<Notification> notifications = new ArrayList<>();
    private ObservableList<Notification> myNotificationList = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        setCNotificationView(notifications);
    }

    private void setCNotificationView(List<Notification> notifications) {
        myNotificationList = FXCollections.observableList(notifications);
        notificatioList.setItems(myNotificationList);
        notificatioList.setCellFactory(new NotificationCellRenderer());
    }

}
