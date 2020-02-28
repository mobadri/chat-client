package com.chat.client.service.client.fileTransfer;

import com.chat.client.service.client.callback.FileTransferServiceCallBack;
import com.chat.server.model.chat.ChatGroup;
import com.chat.server.model.user.User;
import com.healthmarketscience.rmiio.RemoteInputStream;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ClientFileTransferService extends Remote {

    /**
     * register this client on server
     *
     * @param fileTransferServiceCallBack
     * @throws RemoteException
     */
    void register(FileTransferServiceCallBack fileTransferServiceCallBack) throws RemoteException;

    /**
     * unregister this client on server
     *
     * @param fileTransferServiceCallBack
     * @throws RemoteException
     */
    void unregister(FileTransferServiceCallBack fileTransferServiceCallBack) throws RemoteException;

    /**
     * * send file from client server
     *
     * @param remoteInputStream
     * @throws RemoteException
     */
    void sendFile(String nameFile, RemoteInputStream remoteInputStream, ChatGroup currentChatGroup, User currentUser) throws RemoteException;

    /**
     * client accept file from server
     *
     * @param fileName
     * @param currentChatGroupId
     * @param currentUser
     * @throws RemoteException
     */
    void clientAcceptFile(String fileName, int currentChatGroupId, User currentUser) throws RemoteException;
}
