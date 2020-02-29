package com.chat.client.controller.client.message;

import com.chat.server.model.chat.Message;
import com.chat.server.model.user.User;

import java.util.List;

public interface MessageController {
    void saveMessages(User currentUser, List<Message> messageList, String path);

    List<Message> loadMessages(String path);
}
