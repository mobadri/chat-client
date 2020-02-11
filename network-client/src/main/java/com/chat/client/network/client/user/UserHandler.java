package com.chat.client.network.client.user;

import com.chat.server.model.user.User;

import java.util.List;

public interface UserHandler {


    /**
     * search for user by phone
     *
     * @param phone user phone
     * @return user if founded Or null if not founded
     */
    User searchByPhone(String phone);

    /**
     * add to friend list
     *
     * @param currentUser login user
     * @param friend      fried will add to friend list
     * @return true if succeed to add , false if failed
     */
    boolean addFriend(User currentUser, User friend);

    /**
     * remove friend from my friend list
     *
     * @param currentUser login user
     * @param friend      friend will remove from friend list
     * @return true if succeed to remove , false if failed
     */
    boolean removeFriend(User currentUser, User friend);

    /**
     * login to the system
     *
     * @param phone    user phone
     * @param password user password
     * @return user if founded Or null if not founded
     */
    User login(String phone, String password);

    List<User> getAllUsers();

    User signUp(User user);

}
