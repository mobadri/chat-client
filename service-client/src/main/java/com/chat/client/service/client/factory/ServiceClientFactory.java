package com.chat.client.service.client.factory;

import com.chat.client.service.client.user.UserService;
import com.chat.client.service.client.user.impl.UserServiceImpl;

public class ServiceClientFactory {
    private static UserService userService = null;

    private ServiceClientFactory() {

    }

    public static synchronized UserService createUserService() {
        if (userService == null) {
            return new UserServiceImpl();
        }
        return userService;
    }
}
