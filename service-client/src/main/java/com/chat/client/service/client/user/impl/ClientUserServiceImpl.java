package com.chat.client.service.client.user.impl;

import com.chat.client.network.client.factory.NetworkFactory;
import com.chat.client.network.client.user.UserHandler;
import com.chat.client.service.client.user.ClientUserService;
import com.chat.client.service.client.user.validation.UserValidation;
import com.chat.server.model.user.User;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;
import java.util.Map;

public class ClientUserServiceImpl extends UnicastRemoteObject implements ClientUserService {

    private static ClientUserServiceImpl instance;

    private ClientUserServiceImpl() throws RemoteException {
    }

    UserHandler userHandler = NetworkFactory.createUserHandler();
    //@mariam
    //todo impl methods
    // using the factory object from Repository layer

    @Override
    public User login(String phone, String password) {

        return userHandler.login(phone, password);
    }

    @Override
    public int addFriend(User currentUser, User friend) throws RemoteException {
        return userHandler.addFriend(currentUser, friend);
    }

    @Override
    public User existedPhone(String phone) throws RemoteException {
        return userHandler.existedPhone(phone);
   }


    @Override
    public int removeFriend(int currentid,int userid) throws RemoteException {
        return userHandler.removeFriend(currentid,userid);
    }

    @Override
    public Map<String, Boolean> validation(User user) throws RemoteException {
        UserValidation userValidation = new UserValidation(user);
        return userValidation.validUser(user);
    }

    @Override
    public User signup(User user,String password) {
        return userHandler.signUp(user,password);
    }

    @Override
    public List<User> searchByPhone(String phone) throws RemoteException {
        return userHandler.searchByPhone(phone);
    }

    public static synchronized ClientUserServiceImpl createUserServiceInstance() {
        if (instance == null) {
            try {
                instance = new ClientUserServiceImpl();
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
        return instance;
    }
    @Override
    public boolean validatePhone(String phone) throws RemoteException {
        UserValidation userValidation = new UserValidation();
        return userValidation.validPhone(phone);
    }

    @Override
    public User updateUserData(User user,String password) throws RemoteException {
        return userHandler.updateUser(user,password);
    }
    @Override
    public int statusFriend(int userID, int friendID) throws RemoteException {
        return userHandler.friendStatus(userID,friendID);
    }
}