package com.chat.client.controller.client.user;

import com.chat.client.service.client.user.ClientUserService;
import com.chat.server.model.user.User;

public class RegistrationController implements SignUpAndRegistration {
    private ClientUserService clientUserService;

    public RegistrationController(ClientUserService clientUserService) {
        this.clientUserService = clientUserService;
    }

    public User login(String phone, String password) {
        return clientUserService.login(phone, password);
    }

    @Override
    public User signUp(User user) {
        return clientUserService.signup(user);
    }

}
