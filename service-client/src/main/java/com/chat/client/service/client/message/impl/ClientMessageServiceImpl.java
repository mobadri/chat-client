package com.chat.client.service.client.message.impl;

import com.chat.client.network.client.chat.ChatGroupHandler;
import com.chat.client.network.client.factory.NetworkFactory;
import com.chat.client.service.client.message.ClientMessageService;
import com.chat.server.model.chat.Message;

public class ClientMessageServiceImpl implements ClientMessageService {

    //@yassmin
    //todo impl this class
    // using the factory object from Repository layer
//    MessageGroupRepository messageGroupRepository = RepsitoryFactory.creatMessageGroupRepository();
    ChatGroupHandler chatGroupHandler = NetworkFactory.createChatGroupHandler();

    @Override
    public void sendMessage(Message message) {
//        chatGroupHandler.sendMessage(message);
    }

    @Override
    public Message receiveMessage() {
//        return messageGroupRepository.receiveMessage();
        return null;
    }


}
