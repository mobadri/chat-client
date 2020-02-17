package com.chat.client.service.client.chat.impl;

import com.chat.client.service.client.chat.ClientChatGroupService;
import com.chat.server.model.chat.ChatGroup;
import com.chat.server.model.chat.Message;
import com.chat.server.model.user.User;

import java.util.List;

public class ClientChatGroupServiceImpl implements ClientChatGroupService {

    private static  ClientChatGroupServiceImpl instance;

    private ClientChatGroupServiceImpl (){

    }

    @Override
    public ChatGroup createGroup(ChatGroup chatGroup) {
        return null;
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
    public void appendMessage(Message message) {

    }
    //@badri
    //todo impl methods
    // hint : use a repo object from repo layer

    public static synchronized ClientChatGroupServiceImpl createChatGroupServiceInstance() {
        if (instance == null) {
            instance= new ClientChatGroupServiceImpl();
        }
        return instance;
    }
}
