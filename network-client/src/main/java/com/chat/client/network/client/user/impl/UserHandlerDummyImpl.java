package com.chat.client.network.client.user.impl;

import com.chat.client.network.client.user.UserHandler;
import com.chat.server.model.user.Gender;
import com.chat.server.model.user.Mode;
import com.chat.server.model.user.User;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;

public class UserHandlerDummyImpl implements UserHandler {

    User ahmed = new User("ahmed", "shaheen", "01061510304",
            "ahmed", "ahmedshaheen676@yahoo.com", "Egypt",
            Gender.MALE, new Date(), "java developer", new ArrayList<>(),
            new ArrayList<>(), true, Mode.AVAILABLE);
    User ali = new User("ali", "ali", "01212121212",
            "ali", "ali@yahoo.com", "Egypt",
            Gender.MALE, new Date(), "java developer", new ArrayList<>(),
            new ArrayList<>(), true, Mode.AVAILABLE);
    User mohamed = new User("mohamed", "mohamed", "01000000000",
            "mohamed", "mohamed@yahoo.com", "US",
            Gender.MALE, new Date(), "java developer", new ArrayList<>(),
            new ArrayList<>(), true, Mode.AVAILABLE);
    User assmaa = new User("assmaa", "assmaa", "01111111111",
            "assmaa", "assmaa@yahoo.com", "USA",
            Gender.MALE, new Date(), "java developer", new ArrayList<>(),
            new ArrayList<>(), true, Mode.AVAILABLE);
    User sara = new User("sara", "sara", "0122222222222",
            "sara", "sara@yahoo.com", "US",
            Gender.MALE, new Date(), "java developer", new ArrayList<>(),
            new ArrayList<>(), true, Mode.AVAILABLE);
    User alaa = new User("alaa", "alaa", "0133333333333",
            "alaa", "alaa@yahoo.com", "USA",
            Gender.FEMALE, new Date(), "java developer", new ArrayList<>(),
            new ArrayList<>(), false, Mode.BUSY);

    List<User> users = new ArrayList<>();

    public UserHandlerDummyImpl() {

        ahmed.setId(1);
        mohamed.setId(2);
        ali.setId(3);
        assmaa.setId(4);
        sara.setId(5);
        alaa.setId(6);

        users.add(ahmed);
        users.add(mohamed);
        users.add(ali);
        users.add(assmaa);
        users.add(sara);
        users.add(alaa);
    }


    @Override
    public User searchByPhone(String phone) {
        User firend = null;
        try {
            firend = users.parallelStream()
                    .filter(user -> user.getPhone().equals(phone))
                    .findFirst().get();
        } catch (NoSuchElementException e) {
            e.printStackTrace();
            System.out.println("user not founded");
        }
        return firend;
    }

    @Override
    public boolean addFriend(User currentUser, User friend) {
        return currentUser.getFriends().add(friend);
    }

    @Override
    public boolean removeFriend(User currentUser, User friend) {
        return currentUser.getFriends().remove(friend);
    }

    @Override
    public User login(String phone, String password) {
        User currentUser = null;
        try {
            currentUser = users.parallelStream()
                    .filter(user -> user.getPhone().equals(phone) && user.getPassword().equals(password))
                    .findFirst().get();
        } catch (NoSuchElementException e) {
            e.printStackTrace();
            System.out.println("user not founded");
        }
        return currentUser;
    }
}
