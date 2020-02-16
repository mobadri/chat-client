package com.chat.client.controller.client.chatGroup;

import com.chat.server.model.chat.ChatGroup;
import com.chat.server.model.chat.Message;

public interface ChatGroupInterface {

    void sendMessage(Message message);

    void receiveMessage(Message message);

    void setChatGroup(ChatGroup chatGroup);
}
