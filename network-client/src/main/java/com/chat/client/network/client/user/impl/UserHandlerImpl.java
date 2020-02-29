package com.chat.client.network.client.user.impl;

import com.chat.client.network.client.config.NetworkConfig;
import com.chat.client.network.client.user.UserHandler;
import com.chat.server.model.user.FriendStatus;
import com.chat.server.model.user.Mode;
import com.chat.server.model.user.User;
import com.chat.server.model.user.UserFriend;
import com.chat.server.service.server.user.ServerUserService;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.List;

public class UserHandlerImpl implements UserHandler {

    private ServerUserService serverUserService;
    private NetworkConfig networkConfig;

    public UserHandlerImpl() {
        networkConfig = NetworkConfig.getInstance();
        int portNumber = networkConfig.getServerPortNumber();
        String serverIP = networkConfig.getServerIp();
        try {

            /*commented segments of code is connection security trail */
//            Registry registry = LocateRegistry.getRegistry("10.145.7.174", PORT_NUMBER);
            Registry registry = LocateRegistry.getRegistry("localhost",
                    11223/*, SslClientSocketFactory.getInstance()*/);
            /*, NetworkFactory.createSslClientSocketFactory()*/
            ;
//            Registry registry = LocateRegistry.getRegistry(serverIP, portNumber);
            serverUserService = (ServerUserService) registry.lookup("userService");
            System.out.println(serverUserService);

        } catch (RemoteException | NotBoundException e) {
            System.out.println("connection has failed");
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
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public int removeFriend(int currentId, int friendId) {
        try {
            return serverUserService.removeFriend(currentId, friendId);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return 0;
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
    public User existedPhone(String phone) {
        try {
            return serverUserService.getUserByPhone(phone);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public User updateUser(User user, String password) {
        try {
            return serverUserService.updateUser(user, password);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return user;
    }

    @Override
    public void updateUserMode(User user, Mode mode) {
        try {
            serverUserService.updateUserMode(user, mode);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<User> getAllFriendRequests(User currentUser) {
        List<User> userList = new ArrayList<>();
        try {
            System.out.println(userList.size());
            userList = serverUserService.getUserFriends(currentUser, FriendStatus.PENDING);

        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return userList;
    }

    @Override
    public User uniquePhone(String phone) {
        try {
            return serverUserService.uniquePhone(phone);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public int updateFriend(int userId, int friendId, FriendStatus friendStatus) {
//        try {

        try {
            return serverUserService.updateFriend(userId, friendId, friendStatus);
        } catch (RemoteException e) {
            e.printStackTrace();
        }

//        } catch (RemoteException e) {
//            e.printStackTrace();
//        }
        return 0;
    }


    @Override
    public User signUp(User user, String password) {
        try {
            return serverUserService.insertUser(user, password);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return user;
    }

    @Override
    public UserFriend friendStatus(int userID, int friendID) {
        UserFriend userFriend = new UserFriend();

        try {
            userFriend = serverUserService.getStatus(userID, friendID);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return userFriend;
    }
}