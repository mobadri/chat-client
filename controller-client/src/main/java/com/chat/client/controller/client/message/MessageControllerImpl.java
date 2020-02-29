package com.chat.client.controller.client.message;

import com.chat.client.service.client.factory.ServiceClientFactory;
import com.chat.client.service.client.message.ClientMessageService;
import com.chat.server.model.chat.Message;
import com.chat.server.model.user.User;

import java.util.List;

public class MessageControllerImpl implements MessageController {
    ClientMessageService messageService;

    public MessageControllerImpl() {
        messageService = ServiceClientFactory.createMessageService();
    }

    @Override
    public void saveMessages(User currentUser, List<Message> messageList, String path) {
        messageService.saveXmlFile(currentUser, messageList, path);
    }


    @Override
    public List<Message> loadMessages(String path) {
        return messageService.loadFromXml(path);
    }

}
