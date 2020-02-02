package com.chat.client.repository.client.chat;

import com.chat.server.model.chat.ChatGroup;
import com.chat.server.model.chat.Message;
import com.chat.server.model.user.User;

import java.util.List;

public interface ChatGroupRepository {
    /**
     * create new chat group for user
     * @param chatGroup created chat group for current user
     * @return inserted chat group
     */
    ChatGroup createGroup(ChatGroup chatGroup);

    /**
     * remove chat group for user
     * @param chatGroup user chat group
     * @param user current user
     */
    void removeChatGroup(ChatGroup chatGroup , User user);

    /**
     * search for my groups
     * @param groupName chat group name
     * @param user current user
     * @return list of my groups
     */
    List<ChatGroup> searchByName(String groupName, User user);

    /**
     * update chat group
     * @param chatGroup chat group to be updated
     * @return updated chat group
     */
    ChatGroup updateChatGroup(ChatGroup chatGroup);

    /**
     * append message to chat group
     * @param message message to be appended
     */
    void appendMessage(Message message);

}
