package com.chat.client.service.client.callback;


import com.healthmarketscience.rmiio.RemoteInputStream;
import com.healthmarketscience.rmiio.RemoteOutputStream;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface FileTransferServiceCallBack extends Remote {
    /**
     * @param
     * @throws RemoteException
     */
    void downLoad(String nameFile, RemoteInputStream remoteInputStream) throws RemoteException;

    /**
     * get ChatGroupId
     *
     * @return integer represent ChatGroupId that will be recieved the file
     */
    int getChatGroupId() throws RemoteException;

    /**
     * getCurrentUserId represent user that sent the file
     *
     * @return
     */
    int getCurrentUserId() throws RemoteException;


}
