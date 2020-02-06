package com.chat.client.service.client.message.impl;

import com.chat.client.repository.client.factory.RepsitoryFactory;
import com.chat.client.repository.client.message.MessageGroupRepository;
import com.chat.client.service.client.message.ClientMessageService;
import com.chat.server.model.chat.Message;

public class ClientMessageServiceImpl implements ClientMessageService {

    //@yassmin
    //todo impl this class
    // using the factory object from Repository layer
    MessageGroupRepository messageGroupRepository = RepsitoryFactory.creatMessageGroupRepository();

    @Override
    public void sendMessage(Message message) {
        messageGroupRepository.sendMessage(message);
    }

    @Override
    public Message receiveMessage() {
        return messageGroupRepository.receiveMessage();
    }


}
