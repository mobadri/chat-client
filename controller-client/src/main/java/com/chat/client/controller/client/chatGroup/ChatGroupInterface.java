package com.chat.client.controller.client.chatGroup;

import com.chat.server.model.chat.ChatGroup;
import com.chat.server.model.chat.Message;

public interface ChatGroupInterface {

    void sendMessage(Message message, boolean isChatBotEnabled);

    void receiveMessage(Message message);

    void setChatGroup(ChatGroup chatGroup);

    void unregisterService();

    void getChatBotResponse(Message receivedMessage);

    Message createMessage(String messageContent);
}
