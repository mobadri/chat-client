package com.chat.client.network.client.chat.impl;

import com.chat.client.network.client.chat.MessageHandler;
import com.chat.client.network.client.config.NetworkConfig;
import com.chat.client.network.client.socket_factory.RMISSLClientSocketFactory;
import com.chat.client.service.client.callback.MessageServiceCallBack;
import com.chat.server.model.chat.Message;
import com.chat.server.service.server.message.ServerMessageService;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class MessageHandlerImpl implements MessageHandler {

    NetworkConfig networkConfig;
    private ServerMessageService serverMessageService;

    public MessageHandlerImpl() {
        networkConfig = NetworkConfig.getInstance();
        String portNumber =networkConfig.getServerPortNumber();
        String serverIP = networkConfig.getServerIp();
        try {
            /*commented segments of code is connection security trail */

            Registry registry = LocateRegistry.getRegistry(serverIP , Integer.valueOf(portNumber));
            /*Registry registry = LocateRegistry.getRegistry(InetAddress.getLocalHost().getHostName(),
                    PORT_NUMBER, new RMISSLClientSocketFactory());*/
//            Registry registry = LocateRegistry.getRegistry(portNumber);
            serverMessageService = (ServerMessageService) registry.lookup("messageService");

        } catch (RemoteException | NotBoundException e) {
            e.printStackTrace();
            System.out.println("something incorrect happened!!");
        }
    }

    @Override
    public void sendMessage(Message message) {
        try {
            serverMessageService.sendMessage(message);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void register(MessageServiceCallBack messageServiceCallBack) {
        try {
            serverMessageService.register(messageServiceCallBack);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void unRegister(MessageServiceCallBack messageServiceCallBack) {
        try {
            serverMessageService.unRegister(messageServiceCallBack);

        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }
}
