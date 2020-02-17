package com.chat.client.network.client.user;

import com.chat.client.service.client.callback.FileTransferServiceCallBack;

public interface FileTransferHandeler {

    public void sendFile(String fileName, byte[] data, int length);

    void register(FileTransferServiceCallBack fileTransferServiceCallBack);

    void unregister(FileTransferServiceCallBack fileTransferServiceCallBack);
}
