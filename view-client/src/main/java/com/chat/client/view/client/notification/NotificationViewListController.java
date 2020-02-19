package com.chat.client.view.client.notification;

import com.chat.server.model.chat.Notification;
import com.chat.server.model.chat.NotificationType;
import com.chat.server.model.user.User;
import javafx.application.Platform;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
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
    private ListView notificatioListView;

    public NotificationViewListController() {

//        NotificationCellRenderer cellRenderer = new NotificationCellRenderer();
    }

    List<Notification> notifications = new ArrayList<>();

    private ObservableList<Notification> myNotificationList = FXCollections.observableArrayList();
    ListProperty<Notification> memberListProperty = new SimpleListProperty<>();
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        addNotification();
        System.out.println(notifications.size());
        setNotificationView(notifications);
    }


    private void setNotificationView(List<Notification> notifications) {
        myNotificationList = FXCollections.observableList(notifications);

        //memberListProperty.set(myNotificationList);
        //notificatioList.itemsProperty().bindBidirectional(memberListProperty);
        notificatioListView.setItems(myNotificationList);
        notificatioListView.setCellFactory(new NotificationCellRenderer());

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

        User user2 = new User();
        user2.setFirstName("khaled");
        user2.setLastName("hussin");
        Notification notification2 = new Notification();
        notification2.setNotificationType(NotificationType.MESSAGE_RECEIVED);
        notification2.setId(9);
        notification2.setNotificationMessage("sent you a friend requist");
        notification2.setUserFrom(user2);

        notifications.add(notification);
        notifications.add(notification1);
        notifications.add(notification2);
        notifications.add(notification2);
        notifications.add(notification2);

    }

    public void removeNotificationFromUI(Notification notification){
        System.out.println("Notification: " + notification);
        myNotificationList.remove(notification);
        System.out.println(myNotificationList);
        notificatioListView.setItems(myNotificationList);

        //notificatioList.getItems().setAll(myNotificationList);
    }

    public List<Notification> getNotifications() {
        return notifications;
    }
}
