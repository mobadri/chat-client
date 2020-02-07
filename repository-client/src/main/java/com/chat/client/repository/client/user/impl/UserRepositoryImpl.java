package com.chat.client.repository.client.user.impl;
import com.chat.client.repository.client.user.UserRepository;
import com.chat.server.model.user.User;

import java.util.List;

public class UserRepositoryImpl implements UserRepository {

    //@mariam
    //todo impl this class
    // use network layer factory object to do impl
    @Override
    public List<User> findMyFrinds(User user) {

        return null;
    }


    @Override
    public User findByPhone(String phone) {
        return null;
    }


    @Override
    public void addFriend(User user) {
    }

    @Override
    public List<User> findOnlineUsers(User user) {
        return null;
    }

    @Override
    public boolean removefrind(User user) {
        return false;
    }

    @Override
    public User login(String phone, String password) {
        //return userHandler.login(phone, password);
        return null;
    }

    @Override
    public User register(User user) {
        return null;
    }

    @Override
    public void logOut(User user) {
    }

    @Override
    public void rememberMe(String phone, String Password) {
    }

    @Override
    public void forgetPassword(String phone) {
    }

}



