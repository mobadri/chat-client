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
    private final int PORT_NUMBER = 44444;
    ServerUserService serverUserService;

    public UserHandlerImpl() {
        try {
            Registry registry = LocateRegistry.getRegistry("10.145.7.174", PORT_NUMBER);
            serverUserService = (ServerUserService) registry.lookup("userService");
            System.out.println(serverUserService);

        } catch (RemoteException | NotBoundException e) {
            e.printStackTrace();
            System.out.println("something incorrect happened!!");
        }
    }

    @Override
    public List<User> searchByPhone(String phone) {

        try {
            return serverUserService.getByPhone(phone);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public int addFriend(User currentUser, User friend) {
        try {
            return serverUserService.addFriend(currentUser, friend);
        }
        catch (RemoteException  e)
        {
            e.printStackTrace();
        }
        return 0;
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
            return serverUserService.insertUser(user);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return user;
    }
}
