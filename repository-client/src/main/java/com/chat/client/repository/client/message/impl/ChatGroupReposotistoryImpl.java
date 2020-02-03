package com.chat.client.repository.client.message.impl;

import com.chat.client.repository.client.message.MessageGroupRepository;
import com.chat.server.model.chat.Message;

import java.util.List;

public class ChatGroupReposotistoryImpl implements MessageGroupRepository {
    @Override
    public void sendMessage(Message message) {

    }

    @Override
    public List<Message> receiveMessage() {
        return null;
    }
}
