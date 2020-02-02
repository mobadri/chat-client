package com.chat.client.repository.client.user;


import com.chat.server.model.user.User;

import java.util.List;

public interface UserRepository {

    /**
     * find all my friends
     *
     * @param user current user
     * @return list of users as user friends or null if no friends
     */
    List<User> findMyFrinds(User user);

    /**
     * find my friend by phone
     *
     * @param phone user phone
     * @return result of search as user or null if this phone not registered
     */
    User findByPhone(String phone);

    /**
     * add my friend
     *
     * @param user userFriend friend want to add him
     */
    void addFriend(User user);

    /**
     * find online users
     *
     * @return list of شمall my online friends or null if all my friends offline
     */
    List<User> findOnlineUsers(User user);

    /**
     * remove friend
     *
     * @param user user want to remove him
     * @return boolean if this user removed or not
     */
    boolean removefrind(User user);


    /**
     * login on chat
     *
     * @param phone    user phone
     * @param Password user password
     * @return user if found or null if not found
     */
    User login(String phone, String Password);

    /**
     * registration
     *
     * @param user user want to regist
     * @return registered user
     */
    User register(User user);

    /**
     * log out from chat
     *
     * @param user current user
     */
    void logOut(User user);

    /**
     * يdoes not enter phone and password to start chatting
     *
     * @param phone    user phone
     * @param Password password phone
     */
    void rememberMe(String phone, String Password);

    /**
     * forget my password
     *
     * @param phone user phone
     */
    void forgetPassword(String phone);

}
