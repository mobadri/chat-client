package com.chat.client.service.client.user.validation;

import com.chat.client.service.client.factory.ServiceClientFactory;
import com.chat.server.model.user.Gender;
import com.chat.server.model.user.User;

import java.rmi.RemoteException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class UserValidation {


    User user = null;

    public UserValidation(User user) {

        this.user = user;
    }

    public UserValidation() {
    }

    public boolean validName(String name) {

        return name.matches("[a-zA-z]+");
    }

    public boolean validMail(String mail) {
        return mail.matches("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
    }

    public boolean validPhone(String phone) {

        return phone.matches("^(?:\\+?2)?(01)[1250]{1}[0-9]{8}$");
    }

    public boolean gender(Gender gender) {
        return gender != null;
    }


    public boolean validCountry(String country) {

        return !country.equals("");
    }

    public boolean validPassword(String password) {


        return password.matches("^[a-zA-Z!@#$%-^&?_0-9]{8,40}$");

    }

//    public boolean uniquePhone(String phone) {
//
//        ClientUserService clientUserService = ServiceClientFactory.createUserService();
//
//        List<User> listOfUser = clientUserService.
//        for (User u : listOfUser) {
//            if (u.getPhone().equals(phone)) {
//                return false;
//            }
//        }
//        return true;
//    }

    public boolean ValidBio(String bio) {
        return !bio.equals("");

    }

    public Date ValidDate(Date date) {
        return date != null ? date : new Date("");
    }

    public Map<String, Boolean> validUser(User user) {
        User userPhone = null;
        try {
            userPhone = ServiceClientFactory.createUserService().existedPhone(user.getPhone());
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        Map<String, Boolean> validUser = new HashMap<>();

        validUser.put("InvalidFirstName", validName(user.getFirstName()));
        validUser.put("InvalidLastName", validName(user.getLastName()));
        validUser.put("InvalidPhone", validPhone(user.getPhone()));
        validUser.put("InvalidPassword", validPassword(user.getPassword()));
        validUser.put("InvalidEmail", validMail(user.getEmail()));
        validUser.put("InvalidCountry", validCountry(user.getCountry()));
        validUser.put("InvalidGender", gender(user.getGender()));

        return validUser;
    }


}
