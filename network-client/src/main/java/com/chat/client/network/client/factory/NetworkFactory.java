package com.chat.client.network.client.factory;

import com.chat.client.network.client.chat.ChatGroupHandler;
import com.chat.client.network.client.chat.impl.ChatGroupHandlerImpl;
import com.chat.client.network.client.notifocation.NotificationHandler;
import com.chat.client.network.client.notifocation.impl.NotificationHandlerImpl;
import com.chat.client.network.client.user.UserHandler;
import com.chat.client.network.client.user.impl.UserHandlerImpl;

import java.util.Objects;

/**
 * factory class to create object for that layer
 */
public class NetworkFactory {
    private static UserHandler userHandler;
    private static ChatGroupHandler chatGroupHandler;
    private static NotificationHandler notificationHandler;

    private NetworkFactory() {
    }

    /**
     * create UserHandler Object for user over application
     *
     * @return UserHandler implantation
     */
    public static synchronized UserHandler createUserHandler() {
        userHandler = Objects.requireNonNullElseGet(userHandler, () -> new UserHandlerImpl());
        return NetworkFactory.userHandler;
    }

    /**
     * create ChatGroupHandler Object for user over application
     *
     * @return ChatGroupHandler implantation
     */
    public static synchronized ChatGroupHandler createChatGroupHandler() {
        chatGroupHandler = Objects.requireNonNullElseGet(chatGroupHandler, () -> new ChatGroupHandlerImpl());
        return chatGroupHandler;
    }

    /**
     * create NotificationHandler Object for user over application
     *
     * @return NotificationHandlerImplantation
     */
    public static synchronized NotificationHandler createNotificationHandler() {

        if (notificationHandler == null)
            notificationHandler = new NotificationHandlerImpl();
        return notificationHandler;
    }
}
