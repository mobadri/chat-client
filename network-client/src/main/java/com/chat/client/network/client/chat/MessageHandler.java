package com.chat.client.network.client.chat;

import com.chat.client.service.client.callback.MessageServiceCallBack;
import com.chat.server.model.chat.Message;

public interface MessageHandler {
    void sendMessage(Message message);
    /**
     * to register client
     * @param messageServiceCallBack client service to register it
     */
    void register(MessageServiceCallBack messageServiceCallBack  );

    /**
     * to unRegister client
     * @param  messageServiceCallBack client service to register it
     */
    public void unRegister(MessageServiceCallBack messageServiceCallBack);
}
