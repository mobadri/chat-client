package com.chat.client.controller.client.chatGroup;

import com.chat.client.service.client.chat.ClientChatGroupService;
import com.chat.client.service.client.factory.ServiceClientFactory;
import com.chat.server.model.chat.ChatGroup;
import com.chat.server.model.user.User;

public class chatGroupDataContorller {
    private ClientChatGroupService clientChatGroupService = ServiceClientFactory.createClientChatGroupService();

    public chatGroupDataContorller() {
    }

    public ChatGroup createNewChatGroup(ChatGroup chatGroup) {
        return clientChatGroupService.createGroup(chatGroup);
    }

    public ChatGroup addUserToChatGroup(ChatGroup chatGroup, User user) {
        return clientChatGroupService.addUserToGroup(chatGroup, user);
    }
}
