package com.chat.client.service.client.fileTransfer;

import com.chat.client.service.client.callback.FileTransferServiceCallBack;

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
     * send file from server to client
     *
     * @param fileName name of sent file
     * @param data     array  of buffer byte that is written in this file
     * @param length   number of byte will be written
     * @throws RemoteException
     */
    void sendFile(String fileName, byte[] data, int length) throws RemoteException;
}
