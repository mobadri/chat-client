package com.chat.client.controller.client.user;

import com.chat.client.service.client.factory.ServiceClientFactory;
import com.chat.client.service.client.user.ClientUserService;
import com.chat.server.model.user.User;

import java.rmi.RemoteException;

public class HomeController {
    private ClientUserService clientUserService = ServiceClientFactory.createUserService();

    public HomeController() {
    }

    public int addFriend(User currentUser, User friend) {
        try {
            return clientUserService.addFriend(currentUser, friend);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return 0;
    }

}
