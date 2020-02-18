package com.chat.client.view.client.notification;

import com.chat.server.model.chat.Notification;
import com.chat.server.model.chat.NotificationType;
import com.chat.server.model.user.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
    Notification notification = new Notification();

    private ObservableList<Notification> myNotificationList = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        addNotification();
        setNotificationView(notifications);
        NotificationCellRenderer cellRenderer = new NotificationCellRenderer();
    }

    private void setNotificationView(List<Notification> notifications) {
        myNotificationList = FXCollections.observableList(notifications);
        notificatioList.setItems(myNotificationList);
        notificatioList.setCellFactory(new NotificationCellRenderer());
    }

    void addNotification(){
        User user = new User();
        user.setFirstName("ahmed");
        user.setLastName("kamel");
        Notification notification = new Notification();
        notification.setNotificationType(NotificationType.MESSAGE_RECEIVED);
        notification.setId(13);
        notification.setNotificationMessage("sent you a friend requist");
        notification.setUserFrom(user);

        User user1 = new User();
        user1.setFirstName("mohamed");
        user1.setLastName("ali");
        Notification notification1 = new Notification();
        notification1.setNotificationType(NotificationType.MESSAGE_RECEIVED);
        notification1.setId(14);
        notification1.setNotificationMessage("sent you a friend requist");
        notification1.setUserFrom(user1);
        notifications.add(notification);
        notifications.add(notification1);

    }

}
