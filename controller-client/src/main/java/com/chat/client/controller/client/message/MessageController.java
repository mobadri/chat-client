package com.chat.client.controller.client.message;

import com.chat.server.model.chat.Message;

import java.util.List;

public interface MessageController {
    void saveMessages(List<Message> messageList, String path);

    List<Message> loadMessages(String path);
}
