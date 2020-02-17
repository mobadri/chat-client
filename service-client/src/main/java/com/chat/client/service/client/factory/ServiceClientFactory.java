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

    private ServiceClientFactory() {
    }


    public static synchronized ClientUserService createUserService() {
        return ClientUserServiceImpl.createUserServiceInstance();

    }

    public static synchronized ClientNotificationService createNotificationService() {
  return ClientNotificationServiceImpl.createNotificationGroupServiceInstance();
    }

    public static synchronized ClientMessageService createMessageService() {
        return ClientMessageServiceImpl.createMessageGroupServiceInstance();
    }

    public static synchronized ClientChatGroupService createClientChatGroupService(){
        return ClientChatGroupServiceImpl.createChatGroupServiceInstance();
    }

}