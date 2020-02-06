package com.chat.client.repository.client.factory;

import com.chat.client.repository.client.chat.ChatGroupRepository;
import com.chat.client.repository.client.chat.impl.ChatGroupRepotistoryImpl;
import com.chat.client.repository.client.message.MessageGroupRepository;
import com.chat.client.repository.client.message.impl.MessageGroupRepositoryImpl;
import com.chat.client.repository.client.notification.NotificationRepository;
import com.chat.client.repository.client.notification.impl.NotificationRepositoryImpl;
import com.chat.client.repository.client.user.UserRepository;
import com.chat.client.repository.client.user.impl.UserRepositoryImpl;

public class RepsitoryFactory {
    private static ChatGroupRepository chatGroupRepository = null;
    private static MessageGroupRepository messageGroupRepository = null;
    private static NotificationRepository notificationRepository = null;
    private static UserRepository userRepository = null;

    private RepsitoryFactory() {
    }

    /**
     * create singleton repo for ChatGroupRepository
     *
     * @return ChatGroupRepository
     */
    public synchronized static ChatGroupRepository creatChatGroupRepository() {
        if (chatGroupRepository == null) {
            return new ChatGroupRepotistoryImpl();
        }
        return chatGroupRepository;
    }

    /***
     * create singleton repo for MessageGroupRepository
     * @return MessageGroupRepository
     */
    public synchronized static MessageGroupRepository creatMessageGroupRepository() {

        if (messageGroupRepository == null) {
            return new MessageGroupRepositoryImpl();
        }
        return messageGroupRepository;
    }

    /**
     * create singleton repo for NotificationRepository
     *
     * @return NotificationRepository
     */
    public synchronized static NotificationRepository creatNotificationRepository() {

        if (notificationRepository == null) {
            return new NotificationRepositoryImpl();
        }
        return notificationRepository;
    }

    /**
     * create singleton repo for UserRepository
     *
     * @return UserRepository
     */

    public synchronized static UserRepository creatUserRepository() {

        if (userRepository == null) {
            return new UserRepositoryImpl();
        }
        return userRepository;
    }

}
