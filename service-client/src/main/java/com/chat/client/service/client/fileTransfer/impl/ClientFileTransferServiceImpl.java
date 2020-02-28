package com.chat.client.service.client.fileTransfer.impl;

import com.chat.client.network.client.factory.NetworkFactory;
import com.chat.client.network.client.user.FileTransferHandeler;
import com.chat.client.service.client.callback.FileTransferServiceCallBack;
import com.chat.client.service.client.fileTransfer.ClientFileTransferService;
import com.chat.server.model.chat.ChatGroup;
import com.chat.server.model.user.User;
import com.healthmarketscience.rmiio.RemoteInputStream;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class ClientFileTransferServiceImpl extends UnicastRemoteObject implements ClientFileTransferService {

    private static ClientFileTransferService instance = null;
    FileTransferHandeler fileTransferHandeler = NetworkFactory.createFileTransferHandler();

    public ClientFileTransferServiceImpl() throws RemoteException {
    }

    @Override
    public void register(FileTransferServiceCallBack fileTransferServiceCallBack) throws RemoteException {
        fileTransferHandeler.register(fileTransferServiceCallBack);
    }

    @Override
    public void unregister(FileTransferServiceCallBack fileTransferServiceCallBack) throws RemoteException {
        fileTransferHandeler.unregister(fileTransferServiceCallBack);
    }

    public static synchronized ClientFileTransferService createFileTransferServiceInstance() {
        if (instance == null) {
            try {
                instance = new ClientFileTransferServiceImpl();
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
        return instance;
    }

    @Override
    public void sendFile(String nameFile, RemoteInputStream remoteInputStream, ChatGroup currentChatGroup, User currentUser) throws RemoteException {
        fileTransferHandeler.sendFile(nameFile, remoteInputStream, currentChatGroup, currentUser);

    }

//    @Override
//    public void clientAcceptFile(String fileName, int currentChatGroupId, User currentUser) throws RemoteException {
//        fileTransferHandeler.clientAcceptFile(fileName, currentChatGroupId, currentUser);
//    }

    @Override
    public void send(String nameFile, ChatGroup currentChatGroup, User currentUser) throws RemoteException {
        fileTransferHandeler.send(nameFile, currentChatGroup, currentUser);
    }
}
