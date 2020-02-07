package com.chat.client.network.client.chat;

import com.chat.server.model.chat.ChatGroup;
import com.chat.server.model.user.User;

import java.util.List;

public interface ChatGroupHandler {
    //@noura
    //todo add methods signature for this handler
    //and impl this methods on class ChatGroupHandlerDummyImpl

    /**
     * get chat group with id
     * @param id chat group id
     * @return ChatGroup by id
     */
    public ChatGroup getChatGroupByID(int id);

    /**
     * get all chat groups for a user
     * @param user user that register on group
     * @return list of user groups
     */
    public List<ChatGroup> getAllChatGroupsForUser(User user);

    /**
     * delete chatGroup from database
     * @param id chatGroup id to be deleted
     * @return integer number of row deleted or 0 if not deleted
     */
    public int deleteChatGroup(int id);

    /**
     * create new chat group for user
     * @param chatGroup created chat group for current user
     * @return inserted chat group
     */
    int createGroup(ChatGroup chatGroup);

    /**
     * update chat group
     * @param chatGroup chat group to be updated
     * @return updated chat group
     */
    int updateChatGroup(ChatGroup chatGroup);
}