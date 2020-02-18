package com.chat.client.controller.client.chatGroup;

import com.chat.client.service.client.callback.MessageServiceCallBack;
import com.chat.client.service.client.factory.ServiceClientFactory;
import com.chat.client.service.client.message.ClientMessageService;
import com.chat.server.model.chat.ChatGroup;
import com.chat.server.model.chat.Message;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class ChatGroupController extends UnicastRemoteObject implements ChatGroupInterface
        , MessageServiceCallBack {

    private ClientMessageService messageService = ServiceClientFactory.createMessageService();
    private ChatGroupInterface chatGroupInterface;

    private ChatGroup chatGroup;

    public ChatGroupController() throws RemoteException {
        messageService.register(this);
    }


    @Override
    public void sendMessage(Message message)
    {
        messageService.sendMessage(message);
    }


    public void setChatGroupInterface(ChatGroupInterface chatGroupInterface) {
        this.chatGroupInterface = chatGroupInterface;
    }


    @Override
    public void receiveMessage(Message message)
    {
        chatGroupInterface.receiveMessage(message);
    }

    public void setChatGroup(ChatGroup chatGroup) {
        this.chatGroup = chatGroup;
    }

    @Override
    public int getChatGroupId() {

        return chatGroup.getId();
    }


}
