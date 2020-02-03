package com.chat.client.repository.client.chat.impl;

import com.chat.client.repository.client.chat.ChatGroupRepository;
import com.chat.client.repository.client.factory.Factory;
import com.chat.server.model.chat.ChatGroup;
import com.chat.server.model.chat.Message;
import com.chat.server.model.user.User;

import java.util.List;

public class ChatGroupRepotistoryImpl implements ChatGroupRepository {

    //@badri
    //Note create new object from network layer by using factory
    //todo implement this class
    //--------------------------
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
}
