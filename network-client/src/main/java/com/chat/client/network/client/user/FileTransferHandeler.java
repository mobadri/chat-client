package com.chat.client.network.client.user;

import com.chat.client.service.client.callback.FileTransferServiceCallBack;
import com.healthmarketscience.rmiio.RemoteInputStream;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface FileTransferHandeler {

        void sendFile(String nameFile, RemoteInputStream remoteInputStream);

        void register(FileTransferServiceCallBack fileTransferServiceCallBack);

        void unregister(FileTransferServiceCallBack fileTransferServiceCallBack);
}
