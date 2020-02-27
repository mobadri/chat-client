package com.chat.client.service.client.chat.impl;

import com.chat.client.network.client.chat.ChatGroupHandler;
import com.chat.client.network.client.factory.NetworkFactory;
import com.chat.client.service.client.chat.ClientChatGroupService;
import com.chat.server.model.chat.ChatGroup;
import com.chat.server.model.chat.Message;
import com.chat.server.model.user.User;

import java.util.List;

public class ClientChatGroupServiceImpl implements ClientChatGroupService {

    private static ClientChatGroupServiceImpl instance;
    private ChatGroupHandler chatGroupHandler = NetworkFactory.createChatGroupHandler();

    private ClientChatGroupServiceImpl() {

    }

    public static synchronized ClientChatGroupServiceImpl createChatGroupServiceInstance() {
        if (instance == null) {
            instance = new ClientChatGroupServiceImpl();
        }
        return instance;
    }

    @Override
    public void removeChatGroup(ChatGroup chatGroup, User user) {

    }

    @Override
    public List<ChatGroup> searchByName(String groupName, User user) {
        return null;
    }

    @Override
    public ChatGroup updateChatGroup(ChatGroup chatGroup) {
        return null;
    }

    @Override
    public ChatGroup createGroup(ChatGroup chatGroup) {
        return chatGroupHandler.createGroup(chatGroup);
    }

    @Override
    public void appendMessage(Message message) {

    }
    //@badri
    //todo impl methods
    // hint : use a repo object from repo layer

    @Override
    public ChatGroup findById(int id) {
        return chatGroupHandler.getChatGroupByID(id);
    }

    @Override
    public ChatGroup addUserToGroup(ChatGroup chatGroup, User user) {
        return chatGroupHandler.addUser(chatGroup, user);
    }
}
