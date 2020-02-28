package com.chat.client.service.client.callback;


import com.chat.server.model.chat.ChatGroup;
import com.chat.server.model.user.User;
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
    ChatGroup getChatGroup() throws RemoteException;

    /**
     * getCurrentUserId represent user that sent the file
     *
     * @return
     */
    User getCurrentUser() throws RemoteException;


}
