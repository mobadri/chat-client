package com.chat.client.network.client.notifocation;

import com.chat.server.model.chat.Notification;
import com.chat.server.model.chat.NotificationType;
import com.chat.server.model.user.User;

import java.util.List;

public interface NotificationHandler {

    /**
     * get user notifications either it is seen or not
     * @param user user to get his notification
     * @param seen seen Or not seen
     * @return list of user notifications
     */
    public List<Notification> getUserNotification(User user , boolean seen);

    /**
     * get user notification by notification type or it is seen or not
     * @param user user to get his notifications
     * @param seen seen Or not seen
     * @param notificationType notification type
     * @return
     */
    public List<Notification> getUserNotificationByType(User user, boolean seen , NotificationType notificationType);
}
