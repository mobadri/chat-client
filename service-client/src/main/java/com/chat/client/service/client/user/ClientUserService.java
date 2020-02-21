package com.chat.client.service.client.user;

import com.chat.server.model.user.User;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;
import java.util.Map;

public interface ClientUserService extends Remote {
    //@mariam
    //todo add methods signature for this class

    /**
     * User Login used for login to home page
     *
     * @param phone is used to login
     * @param password password of the user
     * @return a user to logged in
     * @throws RemoteException
     */
    User login(String phone, String password) throws RemoteException;

    /**
     * this method to add new Friend
     * @param currentUser the user is logged in
     * @param friend the user will be added
     * @return number of rows of friends
     * @throws RemoteException
     */
    int addFriend(User currentUser, User friend) throws RemoteException;
    /**
     * this method to remove  Friend
     * @param currentId the current user will delete this friend
     * @param friendId the user will be deleted by id
     * @return number of rows of friends will be deleted
     * @throws RemoteException
     */

    int removeFriend(int currentId,int friendId) throws RemoteException;
    /**
     * validate user
     * @param user
     * @return
     * @throws RemoteException
     */
    Map<String, Boolean> validation(User user) throws RemoteException;

    /**
     * User signed up
     * @param user
     * @return user to logged in
     * @throws RemoteException
     */
    User signup(User user) throws RemoteException;

    /**
     * search by phone
     * @param phone to search with
     * @return existed user
     * @throws RemoteException
     */
    User existedPhone(String phone) throws RemoteException;
    /**
     * search of users
     *
     * @param phone used for phone
     * @return
     * @throws RemoteException
     */
    List<User> searchByPhone(String phone) throws RemoteException;

    /**
     * validate phone
     * @param phone to validate
     * @return
     * @throws RemoteException
     */
    boolean validatePhone(String phone) throws RemoteException;

    /**
     * User to be updated
     * @param user
     * @return updated user
     * @throws RemoteException
     */
    User updateUserData(User user) throws RemoteException;
}