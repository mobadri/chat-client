package notification;

import com.chat.server.model.chat.Notification;

public interface NotificationRepository {
    /**
     * fire notification to other users
     *
     * @param notification to be fired to other user
     */
    void fireNotification(Notification notification);

    /**
     * Update the state of notification
     *
     * @param notification
     * @return Notification that will updated
     */
    Notification updateNotification(Notification notification);

}
