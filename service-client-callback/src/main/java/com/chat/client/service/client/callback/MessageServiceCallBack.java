package com.chat.client.service.client.callback;

import com.chat.server.model.chat.Message;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface MessageServiceCallBack extends Remote {
    /**
     * receive messages from my chat groups
     *
     * @param message message to receive
     */
    void receiveMessage(Message message) throws RemoteException;

    /**
     * get register chatGroup id
     *
     * @return chatGroup id
     * @throws RemoteException
     */
    int getChatGroupId() throws RemoteException;

    /**
     * get register user id
     *
     * @return int of user id
     * @throws RemoteException
     */
    int getCurrentUserId() throws RemoteException;
}
