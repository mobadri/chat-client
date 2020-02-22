package com.chat.client.service.client.notification;

import com.chat.client.service.client.callback.MessageServiceCallBack;
import com.chat.client.service.client.callback.NotificationServiceCallback;
import com.chat.server.model.chat.Notification;
import com.chat.server.model.chat.NotificationType;
import com.chat.server.model.user.User;

import java.util.List;

public interface ClientNotificationService {
    /**
     * get user notifications which seen or not seen
     *
     * @param user user to get its notification
     * @param seen seen Or not seen
     * @return list of user notifications
     */
    public List<Notification> getUserNotification(User user, boolean seen);

    /**
     * get user notification which seen or not by notification type
     *
     * @param user             user to get his notifications
     * @param seen             seen Or not seen
     * @param notificationType notification type
     * @return
     */
    public List<Notification> getUserNotificationByType(User user, boolean seen, NotificationType notificationType);

    void register(NotificationServiceCallback notificationServiceCallback);

    void unRegister(NotificationServiceCallback notificationServiceCallback);
}
