package com.chat.client.controller.client.user.login;

import com.chat.server.model.user.User;

import java.rmi.RemoteException;
import java.util.Map;

public interface SignUpAndRegistration {

    User login(String Phone, String Password);

    User signUp(User user);

    Map<String, Boolean> validate(User user) throws RemoteException;

    /**
     * check valid phone or not
     *
     * @param phone user phone
     * @return true if valid or false if not valid
     */
    boolean validatePhone(String phone);

    /**
     * ckeck this phone is registered  or not
     *
     * @param phone user phone
     * @return user if found or null if notfound
     */
    User existedPhone(String phone);



}
