package com.chat.client.service.client.callback;

import com.chat.server.model.chat.Notification;
import com.chat.server.model.user.User;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface NotificationServiceCallback extends Remote {
    /**
     * receive notification from my fiends
     *
     * @param notification notification be received
     */
    void receiveNotification(Notification notification) throws RemoteException;

    int getUserId() throws RemoteException;

    void changeFriendsStatus(User user) throws RemoteException;

    void showOfflineFriends(User user) throws RemoteException;
}
