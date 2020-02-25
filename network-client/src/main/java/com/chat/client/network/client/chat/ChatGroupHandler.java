package com.chat.client.network.client.chat;

import com.chat.server.model.chat.ChatGroup;
import com.chat.server.model.user.User;

import java.util.List;

public interface ChatGroupHandler {

    /**
     * get chat group with id
     *
     * @param id chat group id
     * @return ChatGroup by id
     */
    public ChatGroup getChatGroupByID(int id);

    /**
     * get all chat groups for a user
     *
     * @param user user that register on group
     * @return list of user groups
     */
    public List<ChatGroup> getAllChatGroupsForUser(User user);

    /**
     * delete chatGroup from database
     *
     * @param id chatGroup id to be deleted
     * @return integer number of row deleted or 0 if not deleted
     */
    public int deleteChatGroup(int id);

    /**
     * create new chat group for user
     *
     * @param chatGroup created chat group for current user
     * @return inserted chat group
     */
    public ChatGroup createGroup(ChatGroup chatGroup);

    /**
     * update chat group
     *
     * @param chatGroup chat group to be updated
     * @return updated chat group
     */
    public ChatGroup updateChatGroup(ChatGroup chatGroup);

    /**
     * add user to group
     * @param chatGroup chat group to add user to it
     * @param user user to added
     * @return updated chat group
     */
    public boolean addUser(int chatGroup, int user);

    /**
     * add user to group
     * @param chatGroup chat group to remove user from it
     * @param user user to added
     * @return updated chat group
     */
    public ChatGroup removeUser(ChatGroup chatGroup, User user);

    /**
     * get all chat groups by name
     * @param groupName group name
     * @param user user to search in his groups
     * @return chat groups
     */
    public List<ChatGroup> searchByName(String groupName, User user);
}