package com.chat.client.controller.client.pushNotifications;

import com.chat.server.model.chat.Notification;
import com.chat.server.model.user.User;

import java.rmi.RemoteException;

public interface PushNotificationInterface {

    void receiveNotification(Notification notification);
    void changeFriendsStatus(User user) ;
    void showOfflineFriends(User user) ;
}
