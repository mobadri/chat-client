package com.chat.client.network.client.user;

import com.chat.client.service.client.callback.FileTransferServiceCallBack;
import com.chat.server.model.chat.ChatGroup;
import com.chat.server.model.user.User;
import com.healthmarketscience.rmiio.RemoteInputStream;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface FileTransferHandeler {

    void sendFile(String nameFile, RemoteInputStream remoteInputStream, ChatGroup currentChatGroup, User currentUser);

    void send(String nameFile, ChatGroup currentChatGroup, User currentUser);

    void register(FileTransferServiceCallBack fileTransferServiceCallBack);

    void unregister(FileTransferServiceCallBack fileTransferServiceCallBack);

    //void clientAcceptFile(String fileName, int currentChatGroupId, User currentUser);
}
