package com.chat.client.network.client.user.impl;

import com.chat.client.network.client.user.UserHandler;
import com.chat.server.model.user.User;
import com.chat.server.service.server.user.ServerUserService;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.List;

public class UserHandlerImpl implements UserHandler {
    private final int PORT_NUMBER = 11223;
    ServerUserService serverUserService;

    public UserHandlerImpl() {
        try {
            Registry registry = LocateRegistry.getRegistry(PORT_NUMBER);
            serverUserService = (ServerUserService) registry.lookup("userService");

        } catch (RemoteException | NotBoundException e) {
            e.printStackTrace();
            System.out.println("something incorrect happened!!");
        }
    }

    @Override
    public User searchByPhone(String phone) {
        return null;
    }

    @Override
    public boolean addFriend(User currentUser, User friend) {
        return false;
    }

    @Override
    public boolean removeFriend(User currentUser, User friend) {
        return false;
    }

    @Override
    public User login(String phone, String password) {
        try {
            return serverUserService.getByPhoneAndPassword(phone, password);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<User> getAllUsers() {
        try {
            return serverUserService.getAllUsers();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public User signUp(User user) {
        try {
            //todo remove int i and retrun user after update service
            int i = serverUserService.insertUser(user);
            user.setId(i);

        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return user;
    }
}
