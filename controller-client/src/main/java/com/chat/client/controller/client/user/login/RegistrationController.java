package com.chat.client.controller.client.user.login;

import com.chat.client.service.client.factory.ServiceClientFactory;
import com.chat.client.service.client.user.ClientUserService;
import com.chat.server.model.user.User;

import java.rmi.RemoteException;
import java.util.Map;

public class RegistrationController implements SignUpAndRegistration {
    private ClientUserService clientUserService = ServiceClientFactory.createUserService();

    public RegistrationController() {

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

    @Override
    public Map<String, Boolean> validate(User user) throws RemoteException {
        return clientUserService.validation(user);
    }

    public User isRemembered() {
        // read file xml (userinfo)
        // if found
        //

        return null;
    }


}
