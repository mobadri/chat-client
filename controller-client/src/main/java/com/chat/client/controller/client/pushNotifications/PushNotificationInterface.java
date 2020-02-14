package com.chat.client.controller.client.pushNotifications;

import com.chat.server.model.chat.Notification;

public interface PushNotificationInterface {

    void receiveNotification(Notification notification);
}
