package com.chat.client.network.client.chat;

import com.chat.server.model.chat.Message;

public interface MessageHandler {
    void sendMessage(Message message);

}
