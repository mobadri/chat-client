package com.chat.client.service.client;

import com.chat.client.service.client.user.validation.UserValidation;
import com.chat.server.model.user.User;

public class Main {
    public static void main(String[] args) {

        User user = new User();
        UserValidation userValidation = new UserValidation(user);

        String[] ValidEmailProvider = {"mkyong@yahoo.com",
                "mkyong-100@yahoo.com", "mkyong.100@yahoo.com",
                "mkyong111@mkyong.com", "mkyong-100@mkyong.net",
                "mkyong.100@mkyong.com.au", "mkyong@1.com",
                "mkyong@gmail.com.com", "mkyong+100@gmail.com",
                "mkyong-100@yahoo-test.com"};


        String[] InvalidEmailProvider = {"mkyong", "mkyong@.com.my",
                "mkyong123@gmail.a", "mkyong123@.com", "mkyong123@.com.com",
                ".mkyong@mkyong.com", "mkyong()*@gmail.com", "mkyong@%*.com",
                "mkyong..2002@gmail.com", "mkyong.@gmail.com",
                "mkyong@mkyong@gmail.com", "mkyong@gmail.com.1a"};


        for (int i = 0; i < ValidEmailProvider.length; i++) {
            System.out.println("Email is valid : " + userValidation.validMail(ValidEmailProvider[i]));
        }
        for (int i = 0; i < InvalidEmailProvider.length; i++) {
            System.out.println("Email is valid : " + userValidation.validMail(InvalidEmailProvider[i]));

        }

        System.out.println(userValidation.validPhone("+2010083199"));
    }

}

