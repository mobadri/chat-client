package com.chat.client.service.client.factory;

import com.chat.client.service.client.chat.ClientChatGroupService;
import com.chat.client.service.client.chat.impl.ClientChatGroupServiceImpl;
import com.chat.client.service.client.message.ClientMessageService;
import com.chat.client.service.client.message.impl.ClientMessageServiceImpl;
import com.chat.client.service.client.notification.ClientNotificationService;
import com.chat.client.service.client.notification.impl.ClientNotificationServiceImpl;
import com.chat.client.service.client.user.ClientUserService;
import com.chat.client.service.client.user.impl.ClientUserServiceImpl;

import java.rmi.RemoteException;

public class ServiceClientFactory {
    private static ClientUserService userService = null;
    private static ClientNotificationService notificationService = null;
    private static ClientMessageService messageService = null;
    private static ClientChatGroupService chatGroupService = null;

    private ServiceClientFactory() {

    }

    public static synchronized ClientUserService createUserService() {
        if (userService == null) {
            try {
                return new ClientUserServiceImpl();
            } catch (RemoteException e) {
                e.printStackTrace();
            }

        }
        return userService;
    }

    public static synchronized ClientNotificationService createNotificationService() {
        if (notificationService == null) {
            return new ClientNotificationServiceImpl();
        }
        return notificationService;
    }

    public static synchronized ClientMessageService createMessageService() {
        if (messageService == null) {
            return new ClientMessageServiceImpl();
        }
        return messageService;
    }

    public static synchronized ClientChatGroupService createChatGroupService() {
        if (chatGroupService == null) {
            return new ClientChatGroupServiceImpl();
        }
        return chatGroupService;
    }
}