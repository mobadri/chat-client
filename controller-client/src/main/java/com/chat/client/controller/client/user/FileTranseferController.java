package com.chat.client.controller.client.user;

import com.chat.client.service.client.callback.FileTransferServiceCallBack;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class FileTranseferController extends UnicastRemoteObject implements FileTransferServiceCallBack {
    //ClientFileTransferService clientFileTransferService = ServiceClientFactory.createFileTransferService();

    public FileTranseferController() throws RemoteException {
    }

    @Override
    public void downLoad(String fileName, byte[] data, int length) throws RemoteException {
        try {
            File file = new File(fileName);
            System.out.println(file);
            if (!file.exists()) {
                System.out.println(file.getName());
                file.createNewFile();
            }
            FileOutputStream fileOutputStream = new FileOutputStream(file, true);
            fileOutputStream.write(data, 0, length);
            fileOutputStream.flush();
            fileOutputStream.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

   /* public  void sendFile (String fileName, byte[] data, int length){
        try {
            clientFileTransferService.sendFile(fileName,data,length);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }*/
}
