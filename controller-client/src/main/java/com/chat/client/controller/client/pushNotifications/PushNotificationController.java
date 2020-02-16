package com.chat.client.controller.client.pushNotifications;

import com.chat.client.service.client.callback.NotificationServiceCallback;
import com.chat.client.service.client.factory.ServiceClientFactory;
import com.chat.client.service.client.notification.ClientNotificationService;
import com.chat.server.model.chat.Notification;
import com.chat.server.model.user.User;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class PushNotificationController extends UnicastRemoteObject implements NotificationServiceCallback {
    private ClientNotificationService clientNotificationService = ServiceClientFactory.createNotificationService();
    private PushNotificationInterface pushNotifications;

    private User currentUser;

    public PushNotificationController() throws RemoteException {
        clientNotificationService.register(this);
    }

    @Override
    public void receiveNotification(Notification notification) {
        pushNotifications.receiveNotification(notification);
    }

    @Override
    public int getUserId() {
        return currentUser.getId();
    }

    public void setPushNotifications(PushNotificationInterface pushNotifications) {
        this.pushNotifications = pushNotifications;
    }

    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }
}
