package com.chat.client.service.client.callback;

import com.chat.server.model.chat.Notification;

public interface NotificationServiceCallback {
    /**
     * receive notification from my fiends
     * @param notification notification be received
     */
    void receiveNotification(Notification notification);
}
