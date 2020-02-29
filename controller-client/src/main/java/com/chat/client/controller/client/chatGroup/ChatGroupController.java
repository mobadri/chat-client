package com.chat.client.controller.client.chatGroup;

import com.chat.client.service.client.callback.MessageServiceCallBack;
import com.chat.client.service.client.chat.ChatBotService;
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
    private ChatBotService chatBotService;

    private ChatGroup chatGroup;
    private User currentUser;

    public ChatGroupController() throws RemoteException {
        messageService.register(this);
    }


    @Override
    public void sendMessage(Message message, boolean isChatBotEnabled) {
        if (!isChatBotEnabled)
            new Thread(() -> chatBotService.learn(chatGroup.getMessages(), message)).start();
        messageService.sendMessage(message);
    }

    @Override
    public void getChatBotResponse(Message receivedMessage) {
        if (receivedMessage.getUserFrom().getId() != currentUser.getId()) {
            String messageContent = chatBotService.getMessage(receivedMessage.getMessage());
            Message sentMessage = createMessage(messageContent);
            sendMessage(sentMessage, false);
        }
    }

    @Override
    public Message createMessage(String messageContent) {
        return chatGroupInterface.createMessage(messageContent);
    }


    @Override
    public void receiveMessage(Message message) {
        chatGroupInterface.receiveMessage(message);
    }


    @Override
    public int getChatGroupId() {

        return chatGroup.getId();
    }

    @Override
    public int getCurrentUserId() throws RemoteException {
        return this.currentUser.getId();
    }

    public void setChatGroupInterface(ChatGroupInterface chatGroupInterface) {
        this.chatGroupInterface = chatGroupInterface;
    }

    public void setChatGroup(ChatGroup chatGroup) {
        this.chatGroup = chatGroup;
    }

    public void setCurrentUser(User user) {
        this.currentUser = user;
        chatBotService = ServiceClientFactory.createChatBotService(currentUser.getPhone());
    }

    public void unregisterService() {
        messageService.unRegister(this);
    }

    public List<ChatGroupController> getChatGroupControllerList() {
        return chatGroupControllerList;
    }


}
