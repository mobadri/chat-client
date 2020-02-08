package com.chat.client.service.client.user.validation;

import com.chat.server.model.user.Gender;
import com.chat.server.model.user.User;

import java.util.HashMap;
import java.util.Map;

public class UserValidation {

    User user = null;

    public UserValidation(User user) {

        this.user = user;
    }

    public boolean validName(String name) {

        return name.matches("[a-zA-z]+");
    }

    public boolean validMail(String mail) {
        return mail.matches("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
    }

    public boolean validPhone(String phone) {

        return phone.matches("^(?:\\+?2)?01[0-9]{9}$");
    }

    public boolean gender(Gender gender) {
        return gender != null;
    }


    public boolean validCountry(String country) {

        return country != null;
    }

    public boolean validPassword(String password) {


        return password.matches("a-zA-Z@#$%&?!0-9{8,40}");

    }

    public Map<String, Boolean> validUser(User user) {
        Map<String, Boolean> validUser = new HashMap<>();

        validUser.put("FIRST_NAME", validName(user.getFirstName()));
        validUser.put("LAST_NAME", validName(user.getLastName()));
        validUser.put("PHONE", validPhone(user.getPhone()));
        validUser.put("PASSWORD", validPassword(user.getPassword()));
        validUser.put("EMAIL", validMail(user.getEmail()));
        validUser.put("COUNTRY", validCountry(user.getCountry()));
        validUser.put("GENDER", gender(user.getGender()));

        return validUser;
    }


}
