package com.chat.client.service.client.notification.impl;

import com.chat.client.service.client.notification.NotificationService;
import com.chat.server.model.chat.Notification;
import com.chat.server.model.chat.NotificationType;
import com.chat.server.model.user.User;

import java.util.List;

public class NotificationServiceImpl implements NotificationService {
    @Override
    public List<Notification> getUserNotification(User user, boolean seen) {
        return null;
    }

    @Override
    public List<Notification> getUserNotificationByType(User user, boolean seen, NotificationType notificationType) {
        return null;
    }

}
