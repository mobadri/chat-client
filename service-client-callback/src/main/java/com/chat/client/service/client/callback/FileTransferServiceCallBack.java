package com.chat.client.service.client.callback;


import java.rmi.Remote;
import java.rmi.RemoteException;

public interface FileTransferServiceCallBack extends Remote {
    /**
     * @param fileName name of sent file
     * @param data     array  of buffer byte that is written in this file
     * @param length   number of byte will be written
     * @throws RemoteException
     */
    void downLoad(String fileName, byte[] data, int length) throws RemoteException;

    /**
     * get ChatGroupId
     *
     * @return integer represent ChatGroupId that will be recieved the file
     */
    int getChatGroupId();

    /**
     * getCurrentUserId represent user that sent the file
     *
     * @return
     */
    int getCurrentUserId();


}
