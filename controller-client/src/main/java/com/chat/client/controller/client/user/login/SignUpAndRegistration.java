package com.chat.client.controller.client.user.login;

import com.chat.server.model.user.User;

import java.rmi.RemoteException;
import java.util.Map;

public interface SignUpAndRegistration {

    User login(String Phone, String Password);

    User signUp(User user);

    Map<String, Boolean> validate(User user) throws RemoteException;

}
