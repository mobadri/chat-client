package com.chat.client.service.client.notification.impl;

import com.chat.client.network.client.factory.NetworkFactory;
import com.chat.client.network.client.notifocation.NotificationHandler;
import com.chat.client.service.client.callback.NotificationServiceCallback;
import com.chat.client.service.client.notification.ClientNotificationService;
import com.chat.server.model.chat.Notification;
import com.chat.server.model.chat.NotificationType;
import com.chat.server.model.user.User;

import java.util.List;

public class ClientNotificationServiceImpl implements ClientNotificationService {
    NotificationHandler notificationHandler;
    private static ClientNotificationServiceImpl instance;

    private ClientNotificationServiceImpl(
    ) {
        notificationHandler = NetworkFactory.createNotificationHandler();
    }

    @Override
    public List<Notification> getUserNotification(User user, boolean seen) {
        return null;
    }

    @Override
    public List<Notification> getUserNotificationByType(User user, boolean seen, NotificationType notificationType) {
        return null;
    }

    @Override
    public void register(NotificationServiceCallback notificationServiceCallback) {
        notificationHandler.register(notificationServiceCallback);
    }

    public synchronized static ClientNotificationServiceImpl createNotificationGroupServiceInstance() {
        if (instance == null) {
            instance = new ClientNotificationServiceImpl();
        }
        return instance;
    }

    @Override
    public void unRegister(NotificationServiceCallback notificationServiceCallback) {
        notificationHandler.unregister(notificationServiceCallback);
    }
}
