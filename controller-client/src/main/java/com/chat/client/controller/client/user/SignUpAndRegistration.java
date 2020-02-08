package com.chat.client.controller.client.user;

import com.chat.server.model.user.User;

public interface SignUpAndRegistration {

    User login(String Phone, String Password);

    User signUp(User user);
}
