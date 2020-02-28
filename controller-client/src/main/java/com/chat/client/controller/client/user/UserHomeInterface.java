package com.chat.client.controller.client.user;

import com.chat.server.model.chat.ChatGroup;
import com.chat.server.model.user.Mode;
import com.chat.server.model.user.User;

import java.util.List;

public interface UserHomeInterface {

    void logout(User currentUser);

    /**
     * to change user mode
     *
     * @param currentUser to be updated
     * @param mode        new mode
     */
    void changeMode(User currentUser, Mode mode);

    /**
     * add new friend to friend list
     *
     * @param friend to be added
     */
    void appendFriend(User friend);

    /**
     * add new chat group to groups list
     *
     * @param chatGroup to be added
     */
    void appendChatGroup(ChatGroup chatGroup);

    /**
     * get all user's friends
     *
     * @param currentUser login user
     * @return list of friends
     */
    List<User> getAllFriends(User currentUser);

    /**
     * get all user's groups
     *
     * @param currentUser login user
     * @return list of groups
     */
    List<ChatGroup> getAllChatGroups(User currentUser);

    /**
     * Add Friend to chatGroup
     *
     * @param chatGroup to add on it
     * @param user      to add in the chat
     * @return chatgroup
     */
    ChatGroup addFriendToChatGroup(ChatGroup chatGroup, User user);

}
