package com.chat.client.service.client.fileTransfer.impl;

import com.chat.client.service.client.callback.FileTransferServiceCallBack;
import com.chat.client.service.client.fileTransfer.ClientFileTransferService;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class ClientFileTransferServiceImpl extends UnicastRemoteObject implements ClientFileTransferService {

    public ClientFileTransferServiceImpl() throws RemoteException {
    }

    @Override
    public void register(FileTransferServiceCallBack fileTransferServiceCallBack) throws RemoteException {

    }

    @Override
    public void unregister(FileTransferServiceCallBack fileTransferServiceCallBack) throws RemoteException {

    }

    @Override
    public void sendFile(String fileName, byte[] data, int length) throws RemoteException {

    }
}
