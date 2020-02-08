package com.chat.client.service.client.user.impl;

import com.chat.client.network.client.factory.NetworkFactory;
import com.chat.client.network.client.user.UserHandler;
import com.chat.client.service.client.user.ClientUserService;
import com.chat.server.model.user.User;

public class ClientUserServiceImpl implements ClientUserService {
    UserHandler userHandler = NetworkFactory.createUserHandler();
    //@mariam
    //todo impl methods
    // using the factory object from Repository layer

    @Override
    public User login(String phone, String password) {
        return userHandler.login(phone, password);
    }

    @Override
    public User signup(User user) {
        return userHandler.signUp(user);
    }
}
