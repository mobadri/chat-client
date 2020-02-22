package com.chat.client.network.client.user.impl;

import com.chat.client.network.client.config.NetworkConfig;
import com.chat.client.network.client.user.FileTransferHandeler;
import com.chat.client.service.client.callback.FileTransferServiceCallBack;
import com.chat.server.service.server.fileTransfer.ServerFileTranseferService;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class FileTranseferHandlerImpl implements FileTransferHandeler {
    NetworkConfig networkConfig;
    private ServerFileTranseferService serverFileTranseferService;

    public FileTranseferHandlerImpl() {
        networkConfig = NetworkConfig.getInstance();
        String portNumber =networkConfig.getServerPortNumber();
        String serverIP = networkConfig.getServerIp();

        /*commented segments of code is connection security trail */
        try {
            Registry registry = LocateRegistry.getRegistry(serverIP, Integer.valueOf(portNumber));
            /*Registry registry = LocateRegistry.getRegistry(InetAddress.getLocalHost().getHostName(),
                    PORT_NUMBER, new RMISSLClientSocketFactory());*/
//            Registry registry = LocateRegistry.getRegistry(portNumber);
            serverFileTranseferService = (ServerFileTranseferService) registry.lookup("serverFileTranseferService");

        } catch (RemoteException | NotBoundException e) {
            e.printStackTrace();
            System.out.println("something incorrect happened!!");
        }
    }

    @Override
    public void sendFile(String fileName, byte[] data, int length) {
        //serverFileTranseferService.se
    }

    @Override
    public void register(FileTransferServiceCallBack fileTransferServiceCallBack) {

    }

    @Override
    public void unregister(FileTransferServiceCallBack fileTransferServiceCallBack) {

    }
}
