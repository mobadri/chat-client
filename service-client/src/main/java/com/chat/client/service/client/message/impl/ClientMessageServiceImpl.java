package com.chat.client.service.client.message.impl;

import com.chat.client.network.client.chat.MessageHandler;
import com.chat.client.network.client.factory.NetworkFactory;
import com.chat.client.service.client.callback.MessageServiceCallBack;
import com.chat.client.service.client.message.ClientMessageService;
import com.chat.server.model.chat.Message;

public class ClientMessageServiceImpl implements ClientMessageService {

    //@yassmin
    //todo impl this class
    // using the factory object from Repository layer
    MessageHandler messageHandler = NetworkFactory.createMessageHandler();

    private static ClientMessageServiceImpl instance;

    private ClientMessageServiceImpl() {

    }

    @Override

    public void sendMessage(Message message) {
        messageHandler.sendMessage(message);
    }

    @Override
    public void register(MessageServiceCallBack messageServiceCallBack) {
        messageHandler.register(messageServiceCallBack);
    }

    @Override
    public void unRegister(MessageServiceCallBack messageServiceCallBack) {
        messageHandler.unRegister(messageServiceCallBack);
    }

    public static synchronized ClientMessageServiceImpl createMessageGroupServiceInstance() {
        if (instance == null) {
            instance = new ClientMessageServiceImpl();
        }
        return instance;
    }

}
