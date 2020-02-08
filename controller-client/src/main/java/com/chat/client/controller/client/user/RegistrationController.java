package com.chat.client.controller.client.user;

import com.chat.client.service.client.user.ClientUserService;
import com.chat.server.model.user.User;

import java.rmi.RemoteException;

public class RegistrationController implements SignUpAndRegistration {
    private ClientUserService clientUserService;

    public RegistrationController(ClientUserService clientUserService) {
        this.clientUserService = clientUserService;
    }

    public User login(String phone, String password) {
        try {
            return clientUserService.login(phone, password);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public User signUp(User user) {
        try {
            return clientUserService.signup(user);

        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return null;
    }

}
