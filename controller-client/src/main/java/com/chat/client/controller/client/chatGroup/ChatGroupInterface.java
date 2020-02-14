package com.chat.client.controller.client.chatGroup;

import com.chat.client.service.client.callback.MessageServiceCallBack;
import com.chat.server.model.chat.Message;

import java.rmi.RemoteException;

public interface ChatGroupInterface  {

    void sendMessage(Message message);

    void receiveMessage(Message message);
}
