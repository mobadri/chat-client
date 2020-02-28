package com.chat.client.controller.client.fileTransfer;

import com.chat.client.service.client.callback.FileTransferServiceCallBack;
import com.chat.server.model.chat.ChatGroup;
import com.chat.server.model.user.User;
import com.healthmarketscience.rmiio.RemoteInputStream;

public interface FileTranseferController {

    void sendFile(String nameFile, RemoteInputStream remoteInputStream, ChatGroup currentChatGroup, User currentUser);

    void unregister();

    void setChatGroup(ChatGroup chatGroup);

    void setCurrentUser(User user);

   // void clientAcceptFile(String fileName, int currentChatGroupId, User currentUser);

    void send(String nameFile, ChatGroup currentChatGroup, User currentUser);


}
