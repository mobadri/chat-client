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
    List<User> searchByPhone(String phone);

    /**
     * add to friend list
     *
     * @param currentUser login user
     * @param friend      fried will add to friend list
     * @return 1 if succeed to add , 0 if failed
     */
    int addFriend(User currentUser, User friend);

    /**
     * remove friend from my friend list
     *
     *
     * @param friendid     friend will remove from friend list
     * @return true if succeed to remove , false if failed
     */
    int removeFriend(int currentId,int friendid);

    /**
     * login to the system
     *
     * @param phone    user phone
     * @param password user password
     * @return user if founded Or null if not founded
     */
    User login(String phone, String password);

    User signUp(User user);

    List<User> getAllUsers();


    /**
     *  ckeck this phone is registered  or not
     * @param phone user phone
     * @return user if found or null if notfound
     */
    User exsitedPhone(String phone);




}
