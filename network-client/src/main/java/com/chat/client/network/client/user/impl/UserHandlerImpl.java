package com.chat.client.network.client.user.impl;

import com.chat.client.network.client.user.UserHandler;
import com.chat.server.model.user.User;

public class UserHandlerImpl implements UserHandler {

    public UserHandlerImpl() {
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
        return null;
    }
}
