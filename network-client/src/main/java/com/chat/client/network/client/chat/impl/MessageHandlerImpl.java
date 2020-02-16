package com.chat.client.network.client.chat.impl;

import com.chat.client.network.client.chat.MessageHandler;
import com.chat.client.service.client.callback.MessageServiceCallBack;
import com.chat.server.model.chat.Message;
import com.chat.server.service.server.message.ServerMessageService;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class MessageHandlerImpl implements MessageHandler {

    private final int PORT_NUMBER = 11223;
    private ServerMessageService serverMessageService;

    public MessageHandlerImpl() {
        try {
            Registry registry = LocateRegistry.getRegistry("10.145.7.174" , 11223);
            serverMessageService = (ServerMessageService) registry.lookup("messageService");

        } catch (RemoteException | NotBoundException e) {
            e.printStackTrace();
            System.out.println("something incorrect happened!!");
        }
    }

    @Override
    public void sendMessage(Message message) {
        try {
            serverMessageService.sendMessage(message);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void register(MessageServiceCallBack messageServiceCallBack) {
        try {
            serverMessageService.register(messageServiceCallBack);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void unRegister(MessageServiceCallBack messageServiceCallBack) {
        try {
            serverMessageService.unRegister(messageServiceCallBack);

        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }
}
