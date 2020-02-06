package com.chat.client.service.client.user.impl;

import com.chat.client.repository.client.factory.RepsitoryFactory;
import com.chat.client.repository.client.user.UserRepository;
import com.chat.client.service.client.user.ClientUserService;
import com.chat.server.model.user.User;

public class ClientUserServiceImpl implements ClientUserService {
    UserRepository userRepository = RepsitoryFactory.creatUserRepository();
    //@mariam
    //todo impl methods
    // using the factory object from Repository layer

    @Override
    public User login(String phone, String password) {
        return userRepository.login(phone, password);
    }
}
