package com.chat.client.service.client.fileTransfer.impl;

import com.chat.client.network.client.factory.NetworkFactory;
import com.chat.client.network.client.user.FileTransferHandeler;
import com.chat.client.service.client.callback.FileTransferServiceCallBack;
import com.chat.client.service.client.fileTransfer.ClientFileTransferService;
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

    }

    @Override
    public void unregister(FileTransferServiceCallBack fileTransferServiceCallBack) throws RemoteException {

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
    public void sendFile(String nameFile, RemoteInputStream remoteInputStream) throws RemoteException {
        fileTransferHandeler.sendFile(nameFile, remoteInputStream);

    }
}
