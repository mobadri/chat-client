package com.chat.client.network.client.user.impl;

import com.chat.client.network.client.user.FileTransferHandeler;
import com.chat.client.service.client.callback.FileTransferServiceCallBack;
import com.chat.server.service.server.fileTransfer.ServerFileTranseferService;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class FileTranseferHandlerImpl implements FileTransferHandeler {
    private final int PORT_NUMBER = 44444;
    private ServerFileTranseferService serverFileTranseferService;

    public FileTranseferHandlerImpl() {

        /*commented segments of code is connection security trail */
        try {
            /*Registry registry = LocateRegistry.getRegistry(InetAddress.getLocalHost().getHostName(),
                    PORT_NUMBER, new RMISSLClientSocketFactory());*/
            Registry registry = LocateRegistry.getRegistry(PORT_NUMBER);
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
