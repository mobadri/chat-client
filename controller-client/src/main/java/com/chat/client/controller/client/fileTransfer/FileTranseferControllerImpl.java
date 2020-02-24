package com.chat.client.controller.client.fileTransfer;

import com.chat.client.service.client.callback.FileTransferServiceCallBack;
import com.chat.client.service.client.factory.ServiceClientFactory;
import com.chat.client.service.client.fileTransfer.ClientFileTransferService;
import com.chat.server.model.chat.ChatGroup;
import com.healthmarketscience.rmiio.RemoteInputStream;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class FileTranseferControllerImpl implements FileTransferServiceCallBack
        , FileTranseferController {

    ClientFileTransferService clientFileTransferService;

    public FileTranseferControllerImpl() {
        clientFileTransferService = ServiceClientFactory.createClientFileTransferService();
    }

    //////**//////////////////////////////////**////////////////////////////////////////////
    /*                                    start
    ///////////////////////////////////Part of client call server//////////////////////////
     */

    @Override
    public void sendFile(String nameFile, RemoteInputStream export) {

        try {
            clientFileTransferService.sendFile(nameFile, export);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void register(FileTransferServiceCallBack fileTransferServiceCallBack) {
        try {
            clientFileTransferService.register(fileTransferServiceCallBack);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void unregister(FileTransferServiceCallBack fileTransferServiceCallBack) {
        try {
            clientFileTransferService.unregister(fileTransferServiceCallBack);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    //////**//////////////////////////////////**////////////////////////////////////////////
    /*                                    end
    ///////////////////////////////////Part of client call server//////////////////////////
     */

    //////**//////////////////////////////////**////////////////////////////////////////////
    /*                                    start
    ///////////////////////////////////Part of implement CallBack //////////////////////////
     */

    @Override
    public void setChatGroup(ChatGroup chatGroup) {

    }

    @Override
    public void downLoad(String fileName, byte[] data, int length) throws RemoteException {

    }

    @Override
    public int getChatGroupId() {
        return 0;
    }

    @Override
    public int getCurrentUserId() {
        return 0;
    }
}
