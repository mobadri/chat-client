package com.chat.client.service.client.factory;

import com.chat.client.service.client.chat.ClientChatGroupService;
import com.chat.client.service.client.chat.impl.ClientChatGroupServiceImpl;
import com.chat.client.service.client.fileTransfer.ClientFileTransferService;
import com.chat.client.service.client.message.ClientMessageService;
import com.chat.client.service.client.message.impl.ClientMessageServiceImpl;
import com.chat.client.service.client.notification.ClientNotificationService;
import com.chat.client.service.client.notification.impl.ClientNotificationServiceImpl;
import com.chat.client.service.client.user.ClientUserService;
import com.chat.client.service.client.user.impl.ClientUserServiceImpl;

public class ServiceClientFactory {
    private static ClientUserService userService = null;
    private static ClientNotificationService notificationService = null;
    private static ClientMessageService messageService = null;
    private static ClientChatGroupService chatGroupService = null;
    private static ClientFileTransferService clientFileTransferService = null;

    private ServiceClientFactory() {
    }


    public static ClientUserService createUserService() {
        return ClientUserServiceImpl.createUserServiceInstance();

    }

    public static ClientNotificationService createNotificationService() {
        return ClientNotificationServiceImpl.createNotificationGroupServiceInstance();
    }

    public static ClientMessageService createMessageService() {
        return ClientMessageServiceImpl.createMessageGroupServiceInstance();
    }

    public static ClientChatGroupService createClientChatGroupService() {
        return ClientChatGroupServiceImpl.createChatGroupServiceInstance();
    }


}