package com.chat.client.service.client.message;

import com.chat.client.service.client.callback.MessageServiceCallBack;
import com.chat.server.model.chat.Message;

import java.util.List;

public interface ClientMessageService {
    //@yassmin
    //todo add methods signature for this class

    /**
     * send message to group
     *
     * @param message is sent to users
     */
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
