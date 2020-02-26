package com.chat.client.controller.client.fileTransfer;

import com.chat.client.service.client.callback.FileTransferServiceCallBack;
import com.chat.server.model.chat.ChatGroup;
import com.chat.server.model.user.User;
import com.healthmarketscience.rmiio.RemoteInputStream;

public interface FileTranseferController {

    void sendFile(String nameFile, RemoteInputStream export);

    void register(FileTransferServiceCallBack fileTransferServiceCallBack);

    void unregister(FileTransferServiceCallBack fileTransferServiceCallBack);

    void setChatGroup(ChatGroup chatGroup);

    void setCurrentUser(User user);
}
