package com.chat.client.controller.client.user;

import com.chat.client.service.client.chat.ClientChatGroupService;
import com.chat.client.service.client.factory.ServiceClientFactory;
import com.chat.client.service.client.user.ClientUserService;
import com.chat.server.model.chat.ChatGroup;
import com.chat.server.model.user.Mode;
import com.chat.server.model.user.User;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

public class HomeControllerImpl implements UserHomeInterface {

    private UserHomeInterface userHomeInterface;
    private ClientUserService userService = ServiceClientFactory.createUserService();
    private ClientChatGroupService chatGroupService = ServiceClientFactory.createClientChatGroupService();
    private List<User> friends = new ArrayList<>();
    private List<ChatGroup> groups = new ArrayList<>();
    ChatGroup chatGroup;

    //todo noura impl
    @Override
    public void logout(User currentUser) {



    }

    @Override
    public void changeMode(User currentUser, Mode mode) {
        try {
            userService.updateUserMode(currentUser, mode);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void appendFriend(User friend) {
        friends.add(friend);
    }

    @Override
    public ChatGroup appendChatGroup(ChatGroup chatGroup) {
        ChatGroup group = chatGroupService.createGroup(chatGroup);

        groups.add(chatGroup);
        return group;
    }

    @Override
    public List<User> getAllFriends(User currentUser) {
        friends = currentUser.getFriends();
        return friends;
    }

    @Override
    public List<ChatGroup> getAllChatGroups(User currentUser) {
        groups = currentUser.getChatGroups();
        return groups;
    }

    @Override
    public ChatGroup addFriendToChatGroup(ChatGroup chatGroup2, User user) {
        return chatGroupService.addUserToGroup(chatGroup2, user);

    }

    public void setUserHomeInterface(UserHomeInterface userHomeInterface) {
        this.userHomeInterface = userHomeInterface;
    }
}