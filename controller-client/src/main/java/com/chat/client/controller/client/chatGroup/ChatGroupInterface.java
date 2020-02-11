package com.chat.client.controller.client.chatGroup;

import com.chat.server.model.chat.Message;

public interface ChatGroupInterface {

    void sendMessage(Message message);
}
