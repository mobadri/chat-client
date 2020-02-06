package com.chat.client.controller.client.user;

import com.chat.client.service.client.factory.ServiceClientFactory;
import com.chat.client.service.client.user.ClientUserService;
import com.chat.server.model.user.User;

public class UserController {
    private ClientUserService userService = ServiceClientFactory.createUserService();

    public UserController() {
    }


    public User login(String phone, String password) {
        return userService.login(phone, password);
    }
}
