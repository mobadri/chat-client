package com.chat.client.controller.client.chatGroup;

import com.chat.client.service.client.factory.ServiceClientFactory;
import com.chat.client.service.client.message.ClientMessageService;
import com.chat.server.model.chat.Message;

public class ChatGroupController implements ChatGroupInterface {
    ClientMessageService messageService = ServiceClientFactory.createMessageService();
    public ChatGroupController(){}
    @Override
    public void sendMessage(Message message) {
        messageService.sendMessage(message);
    }
}
