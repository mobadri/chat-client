package com.chat.client.service.client.chat;

import com.chat.server.model.chat.ChatGroup;
import com.chat.server.model.chat.Message;
import com.chat.server.model.user.User;

import java.util.List;

public interface ClientChatGroupService {
    //@badri
    //todo add methods signature for this class

    /**
     * create new chat group for user
     *
     * @param chatGroup created chat group for current user
     * @return inserted chat group
     */
    ChatGroup createGroup(ChatGroup chatGroup);

    /**
     * remove chat group for user
     *
     * @param chatGroup user chat group
     * @param user      current user
     */
    void removeChatGroup(ChatGroup chatGroup, User user);

    /**
     * search for my groups
     *
     * @param groupName chat group name
     * @param user      current user
     * @return list of my groups
     */
    List<ChatGroup> searchByName(String groupName, User user);

    /**
     * update chat group
     *
     * @param chatGroup chat group to be updated
     * @return updated chat group
     */
    ChatGroup updateChatGroup(ChatGroup chatGroup);

    /**
     * append message to chat group
     *
     * @param message message to be appended
     */
    void appendMessage(Message message);

    /**
     * get full chatgroup data by id
     *
     * @param id chatgroup id
     * @return chatgroup with full data
     */
    ChatGroup findById(int id);


    boolean addUserToGroup(int chatGroup,int user);




}
