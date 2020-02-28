package com.chat.client.controller.client.fileTransfer;

import com.chat.client.service.client.callback.FileTransferServiceCallBack;
import com.chat.client.service.client.factory.ServiceClientFactory;
import com.chat.client.service.client.fileTransfer.ClientFileTransferService;
import com.chat.server.model.chat.ChatGroup;
import com.chat.server.model.user.User;
import com.healthmarketscience.rmiio.*;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.ReadableByteChannel;
import java.nio.channels.WritableByteChannel;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class FileTranseferControllerImpl extends UnicastRemoteObject implements FileTransferServiceCallBack
        , FileTranseferController {

    private ClientFileTransferService clientFileTransferService;
    private static FileTranseferController instance;

    private ChatGroup chatGroup;
    private User currentUser;
    private String storagePath = "C:/Users/pc/Downloads/mariam/";

    public FileTranseferControllerImpl() throws RemoteException {
        clientFileTransferService = ServiceClientFactory.createClientFileTransferService();
        try {
            clientFileTransferService.register(this);
            System.out.println("i register +******************************");
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    //////**//////////////////////////////////**////////////////////////////////////////////
    /*                                    start
    ///////////////////////////////////Part of client call server//////////////////////////
     */

    @Override
    public void sendFile(String nameFile, RemoteInputStream export, ChatGroup currentChatGroup, User currentUser) {

        try {
            clientFileTransferService.sendFile(nameFile, export, currentChatGroup, currentUser);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void unregister() {
        try {
            clientFileTransferService.unregister(this);
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
    public ChatGroup getChatGroup() {
        System.out.println("chatGroup in call back " + chatGroup);
        return chatGroup;
    }

    @Override
    public void setChatGroup(ChatGroup chatGroup) {
        System.out.println("chatgroup in controller implement  call back  ");
        this.chatGroup = chatGroup;

    }

    @Override
    public void downLoad(String nameFile, RemoteInputStream remoteInputStream) throws RemoteException {

        InputStream fileData = null;
        ByteBuffer buffer = null;
        WritableByteChannel to = null;
        ReadableByteChannel from = null;
        try {
            fileData = RemoteInputStreamClient.wrap(remoteInputStream);

            from = Channels.newChannel(fileData);
            System.out.println(fileData);
            buffer = ByteBuffer.allocateDirect(fileData.available());
            String home = System.getProperty("user.home");
            to = FileChannel.open(Paths.get(home + "/Downloads/client/" + nameFile), StandardOpenOption.WRITE, StandardOpenOption.CREATE_NEW);
            while ((from.read(buffer) != -1)) {
                buffer.flip();
                while (buffer.hasRemaining()) {
                    System.out.println("server write ........");
                    to.write(buffer);
                }
                buffer.clear();
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (to != null) {
                    to.close();
                }
                if (from != null) {
                    from.close();
                }
                if (fileData != null) {
                    fileData.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


//            InputStream inputStream = RemoteInputStreamClient.wrap(remoteOutputStream);
//            FileOutputStream fileOutputStream = new FileOutputStream(new File(storagePath + nameFile));
//            byte[] data = new byte[1024 * 1024*1024];+
//            int len = inputStream.read(data);
//            while (len != -1) {
//                fileOutputStream.write(data);
//                len = inputStream.read(data);
//            }
//            System.out.println("file is Downloded ");
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

        // FileOutputStream fileOutputStream=remoteOutputStream


    }

    @Override
    public User getCurrentUser() {
        return currentUser;
    }

    @Override
    public void setCurrentUser(User user) {
        this.currentUser = user;
    }

    @Override
    public void clientAcceptFile(String fileName, int currentChatGroupId, User currentUser) {
        try {
            clientFileTransferService.clientAcceptFile(fileName, currentChatGroupId, currentUser);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public void setClientFileTransferService(ClientFileTransferService clientFileTransferService) {
        this.clientFileTransferService = clientFileTransferService;
    }

}
