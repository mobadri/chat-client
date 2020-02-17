package com.chat.client.controller.client.user;

import com.chat.client.service.client.chat.ClientChatGroupService;
import com.chat.client.service.client.factory.ServiceClientFactory;
import com.chat.client.service.client.user.ClientUserService;
import com.chat.server.model.chat.ChatGroup;
import com.chat.server.model.user.User;

import java.rmi.RemoteException;

public class HomeController {
    private ClientUserService clientUserService = ServiceClientFactory.createUserService();
    private ClientChatGroupService clientChatGroupService = ServiceClientFactory.createClientChatGroupService();


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

    public ChatGroup getById(int id) {
        return clientChatGroupService.findById(id);
    }
}
