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

    ClientFileTransferService clientFileTransferService;
    private ChatGroup chatGroup;
    private User currentUser;
    private String storagePath = "C:/Users/pc/Downloads/mariam/";

    public FileTranseferControllerImpl() throws RemoteException {
        clientFileTransferService = ServiceClientFactory.createClientFileTransferService();
        try {
            clientFileTransferService.register(this);
            System.out.println("i register");
        } catch (RemoteException e) {
            e.printStackTrace();
        }
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

    public void setChatGroup(ChatGroup chatGroup) {
        this.chatGroup = chatGroup;

    }

    public void setCurrentUser(User user) {
        this.currentUser = user;
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
                    System.out.println("server write");
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













/*


        File file;
        try {
            if (remoteInputStream != null){
                InputStream istream = RemoteInputStreamClient.wrap(remoteInputStream);
                ReadableByteChannel readableByteChannel = Channels.newChannel(istream);
                ByteBuffer byteBuffer = ByteBuffer.allocate( istream.available());
                String home = System.getProperty("user.home");
                WritableByteChannel writableByteChannel = FileChannel.open(Paths.get(home + "/Downloads/"+ nameFile ),
                        StandardOpenOption.WRITE ,StandardOpenOption.APPEND, StandardOpenOption.CREATE_NEW );
                while (readableByteChannel.read(byteBuffer) != -1){
                    byteBuffer.flip();
                    while (byteBuffer.hasRemaining()){
                        writableByteChannel.write(byteBuffer);
                    }
                    byteBuffer.clear();
                }

            }else{
                System.out.println("Remote input stream is nulll");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

 */

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
    public int getChatGroupId() {
        return chatGroup.getId();
    }

    @Override
    public int getCurrentUserId() {
        return currentUser.getId();
    }
}
