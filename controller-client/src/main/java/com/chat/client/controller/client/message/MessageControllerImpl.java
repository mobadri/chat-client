package com.chat.client.controller.client.message;

import com.chat.client.service.client.factory.ServiceClientFactory;
import com.chat.client.service.client.message.ClientMessageService;
import com.chat.server.model.chat.Message;

import java.util.List;

public class MessageControllerImpl implements MessageController {
    ClientMessageService messageService;

    public MessageControllerImpl() {
        messageService = ServiceClientFactory.createMessageService();
    }

    @Override
    public void saveMessages(List<Message> messageList, String path) {
        messageService.saveXmlFile(messageList, path);
    }


    @Override
    public List<Message> loadMessages(String path) {
        return messageService.loadFromXml(path);
    }


}
