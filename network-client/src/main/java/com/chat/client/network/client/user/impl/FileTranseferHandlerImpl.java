package com.chat.client.network.client.user.impl;

import com.chat.client.network.client.config.NetworkConfig;
import com.chat.client.network.client.user.FileTransferHandeler;
import com.chat.client.service.client.callback.FileTransferServiceCallBack;
import com.chat.server.model.chat.ChatGroup;
import com.chat.server.model.user.User;
import com.chat.server.service.server.fileTransfer.ServerFileTranseferService;
import com.healthmarketscience.rmiio.RemoteInputStream;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class FileTranseferHandlerImpl implements FileTransferHandeler {
    NetworkConfig networkConfig;
    private ServerFileTranseferService serverFileTranseferService;

    public FileTranseferHandlerImpl() {
        networkConfig = NetworkConfig.getInstance();
        int portNumber = networkConfig.getServerPortNumber();
        String serverIP = networkConfig.getServerIp();

        /*commented segments of code is connection security trail */
        try {
            Registry registry = LocateRegistry.getRegistry(serverIP, portNumber);
            /*Registry registry = LocateRegistry.getRegistry(InetAddress.getLocalHost().getHostName(),
                    PORT_NUMBER, new RMISSLClientSocketFactory());*/
//            Registry registry = LocateRegistry.getRegistry(portNumber);
            serverFileTranseferService = (ServerFileTranseferService) registry.lookup("fileTranseferService");

        } catch (RemoteException | NotBoundException e) {
            e.printStackTrace();
            System.out.println("something incorrect happened!!");
        }
    }

    @Override
    public void sendFile(String nameFile, RemoteInputStream remoteInputStream, ChatGroup currentChatGroup, User currentUser) {
        try {

            serverFileTranseferService.sendFile(nameFile, remoteInputStream, currentChatGroup, currentUser);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void register(FileTransferServiceCallBack fileTransferServiceCallBack) {
        try {
            serverFileTranseferService.register(fileTransferServiceCallBack);
        } catch (RemoteException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void unregister(FileTransferServiceCallBack fileTransferServiceCallBack) {
        try {
            serverFileTranseferService.unregister(fileTransferServiceCallBack);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void clientAcceptFile(String fileName, int currentChatGroupId, User currentUser) {
        try {
            serverFileTranseferService.clientAcceptFile(fileName, currentChatGroupId, currentUser);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }
}
