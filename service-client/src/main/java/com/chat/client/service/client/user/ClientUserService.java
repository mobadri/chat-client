package com.chat.client.service.client.user;

import com.chat.server.model.user.User;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Map;

public interface ClientUserService extends Remote {
    //@mariam
    //todo add methods signature for this class

    User login(String phone, String password) throws RemoteException;

    User registration(User user) throws RemoteException;

    int addFriend(User currentUser, User friend) throws RemoteException;

    public Map<String, Boolean> validation(User user) throws RemoteException;

    User signup(User user) throws RemoteException;
}
