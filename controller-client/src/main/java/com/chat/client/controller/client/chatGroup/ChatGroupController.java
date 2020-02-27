package com.chat.client.controller.client.chatGroup;

import com.chat.client.service.client.callback.MessageServiceCallBack;
import com.chat.client.service.client.factory.ServiceClientFactory;
import com.chat.client.service.client.message.ClientMessageService;
import com.chat.server.model.chat.ChatGroup;
import com.chat.server.model.chat.Message;
import com.chat.server.model.user.User;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

public class ChatGroupController extends UnicastRemoteObject implements ChatGroupInterface
        , MessageServiceCallBack {

    private ClientMessageService messageService = ServiceClientFactory.createMessageService();
    private List<ChatGroupController> chatGroupControllerList = new ArrayList<>();
    private ChatGroupInterface chatGroupInterface;

    private ChatGroup chatGroup;
    private User currentUser;

    public ChatGroupController() throws RemoteException {
        messageService.register(this);
    }


    @Override
    public void sendMessage(Message message) {

        messageService.sendMessage(message);
    }


    public void setChatGroupInterface(ChatGroupInterface chatGroupInterface) {
        this.chatGroupInterface = chatGroupInterface;
    }


    @Override
    public void receiveMessage(Message message) {
        chatGroupInterface.receiveMessage(message);
    }

    @Override
    public void setChatGroup(ChatGroup chatGroup) {
        this.chatGroup = chatGroup;
    }


    public void setCurrentUser(User user) {
        this.currentUser = user;
    }

    @Override
    public int getChatGroupId() {

        return chatGroup.getId();
    }

    @Override
    public int getCurrentUserId() {
        return this.currentUser.getId();
    }

    public void unregisterService() {
        messageService.unRegister(this);
    }

    public List<ChatGroupController> getChatGroupControllerList() {
        return chatGroupControllerList;
    }
}
