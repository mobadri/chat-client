package com.chat.client.service.client.callback;

import com.chat.server.model.chat.Notification;

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
}
