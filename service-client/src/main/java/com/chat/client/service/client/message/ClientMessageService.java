package com.chat.client.service.client.message;

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
     * this method used to recievethemessage from group
     *
     * @return message that from group
     */
    List<Message> receiveMessage();
}
