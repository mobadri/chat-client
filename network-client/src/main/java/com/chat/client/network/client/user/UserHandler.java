package com.chat.client.network.client.user;

import com.chat.server.model.user.Mode;
import com.chat.server.model.user.User;

import java.rmi.RemoteException;
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
     * @param friendId     friend will remove from friend list
     * @return true if succeed to remove , false if failed
     */
    int removeFriend(int currentId,int friendId);

    /**
     * login to the system
     *
     * @param phone    user phone
     * @param password user password
     * @return user if founded Or null if not founded
     */
    User login(String phone, String password);

    User signUp(User user,String password);

    List<User> getAllUsers();


    /**
     *  ckeck this phone is registered  or not
     * @param phone user phone
     * @return user if found or null if notfound
     */
    User existedPhone(String phone);

    /**
     * update user data
     *
     * @param user to be updated
     * @return updated user
     */
    User updateUser(User user,String password);
    /**
     * friend status
     * @param userID id for the user
     * @param friendID id for the friend
     * @return number of status;
     */
    int friendStatus (int userID,int friendID);

    /**
     * User to be updated
     * @param user to update his mode
     * @param mode new mode
     */
    void updateUserMode(User user, Mode mode);
}