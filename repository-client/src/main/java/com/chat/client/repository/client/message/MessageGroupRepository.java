package com.chat.client.repository.client.message;

import com.chat.server.model.chat.Message;

public interface MessageGroupRepository {
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
    Message receiveMessage();


}
