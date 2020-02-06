package com.chat.client.service.client.factory;

import com.chat.client.service.client.user.ClientUserService;
import com.chat.client.service.client.user.impl.ClientUserServiceImpl;

public class ServiceClientFactory {
    private static ClientUserService userService = null;

    private ServiceClientFactory() {

    }

    public static synchronized ClientUserService createUserService() {
        if (userService == null) {
            return new ClientUserServiceImpl();
        }
        return userService;
    }
}
