package com.chat.client.controller.client.user;

import com.chat.client.service.client.chat.ClientChatGroupService;
import com.chat.client.service.client.factory.ServiceClientFactory;
import com.chat.client.service.client.user.ClientUserService;
import com.chat.server.model.chat.ChatGroup;
import com.chat.server.model.user.User;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

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

    public int removeFriend(int currentid, int friendid) {
        try {
            return clientUserService.removeFriend(currentid, friendid);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public ChatGroup getById(int id) {
        return clientChatGroupService.findById(id);
    }

    public List<User> findByPhone(String phone) {
        try {
            return clientUserService.searchByPhone(phone);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return null;
    }

    public int getSatatus(int userId, int friendId) {
        try {
            return clientUserService.statusFriend(userId, friendId);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public List<User> getFriendRequest(User currentUser) {
        List<User> userList = new ArrayList<>();
        try {
            userList = clientUserService.getAllFriendRequests(currentUser);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return userList;
    }
}