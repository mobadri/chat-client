package com.chat.client.service.client.user.validation;

import com.chat.client.service.client.factory.ServiceClientFactory;
import com.chat.server.model.user.Gender;
import com.chat.server.model.user.User;

import java.rmi.RemoteException;
import java.time.LocalDate;
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
        if (name != null) {
            return name.matches("[a-zA-z]+");
        }
        return false;
    }


    public boolean validMail(String mail) {
        if (mail != null) {
            return mail.matches("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                    + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
        }
        return false;
    }

    public boolean validPhone(String phone) {

        if (phone != null) {
            return phone.matches("^(?:\\+?2)?01[0125]{1}[0-9]{8}$");
        }
        return false;
    }



    public boolean gender(Gender gender) {
        return gender != null;
    }


    public boolean validCountry(String country) {
        if (country != null) {
            return !country.equals("");
        }
        return false;
    }

    public boolean validPassword(String password) {


        return password.matches("^[a-zA-Z!@#$%-^&?_0-9]{8,40}$");

    }

    private Boolean validDateOfBirth(LocalDate dateOfBirth) {
        if (dateOfBirth != null) {
            return !dateOfBirth.equals("");

        }
        return false;
    }

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
        validUser.put("InvalidPhone", validPhone(user.getPhone()) && userPhone == null);
        validUser.put("InvalidPassword", validPassword(user.getPassword()));
        validUser.put("InvalidEmail", validMail(user.getEmail()));
        validUser.put("InvalidCountry", validCountry(user.getCountry()));
        validUser.put("InvalidGender", gender(user.getGender()));
        validUser.put("InvalidDateOfBirth", validDateOfBirth(user.getDateOfBirth()));

        return validUser;
    }



}
