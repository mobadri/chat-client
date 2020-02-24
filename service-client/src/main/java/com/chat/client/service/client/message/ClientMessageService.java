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
     *
     * @param messageServiceCallBack client service to register it
     */
    void register(MessageServiceCallBack messageServiceCallBack);

    /**
     * to unRegister client
     *
     * @param messageServiceCallBack client service to register it
     */
    void unRegister(MessageServiceCallBack messageServiceCallBack);

    /**
     * save list of messages to xml file
     *
     * @param messageList list of message on the chat group
     * @param path        path of file to save on
     */
    void saveXmlFile(List<Message> messageList, String path);

    /**
     * load list of messages from xml file
     *
     * @param path path of xml to load messages
     * @return list of messages from xml
     */
    List<Message> loadFromXml(String path);

}
