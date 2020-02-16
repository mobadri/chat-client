package com.chat.client.network.client.chat.impl;

import com.chat.client.network.client.chat.ChatGroupHandler;
import com.chat.server.model.chat.ChatGroup;
import com.chat.server.model.user.User;
import com.chat.server.service.server.chatgroup.ServerChatGroupService;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.List;

public class ChatGroupHandlerImpl implements ChatGroupHandler {

    private final int PORT_NUMBER = 11223;
    ServerChatGroupService serverChatGroupService;

    public ChatGroupHandlerImpl() {

        try {
            Registry registry = LocateRegistry.getRegistry("10.145.7.174", PORT_NUMBER);
            serverChatGroupService = (ServerChatGroupService) registry.lookup("chatGroupService");
        } catch (RemoteException | NotBoundException e) {
            e.printStackTrace();
            System.out.println("something incorrect happened!!");
        }
    }

    @Override
    public ChatGroup getChatGroupByID(int id) {
        try {
            return serverChatGroupService.getChatGroupByID(id);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<ChatGroup> getAllChatGroupsForUser(User user) {
        try {
            return serverChatGroupService.getAllChatGroupsForUser(user);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public int deleteChatGroup(int id) {
        try {
            return serverChatGroupService.deleteChatGroup(id);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public ChatGroup createGroup(ChatGroup chatGroup) {

        try {
            return serverChatGroupService.insertChatGroup(chatGroup);
        } catch (RemoteException e) {
            System.out.println("something incorrect happened!! " + e);
        }
        return chatGroup;
    }

    @Override
    public ChatGroup updateChatGroup(ChatGroup chatGroup) {

        try {
            return serverChatGroupService.updateChatGroup(chatGroup);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return chatGroup;
    }

    @Override
    public ChatGroup addUser(ChatGroup chatGroup, User user) {
        try {
            return serverChatGroupService.addFriend(chatGroup, user);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public ChatGroup removeUser(ChatGroup chatGroup, User user) {
        try {
            return serverChatGroupService.removeFriend(chatGroup, user);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<ChatGroup> searchByName(String groupName, User user) {
        try {
            return serverChatGroupService.searchByName(groupName, user);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return null;
    }
}