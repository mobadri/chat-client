package com.chat.client.service.client.chat;

import chatbot.ChatBotInterface;
import com.chat.server.model.chat.Message;

import java.util.List;

public interface ChatBotService{

    /**
     * get response message from chat bot
     * @param message sent message from friend
     * @return response message
     */
    public String getMessage(String message);

    /**
     * to make chat bot learn
     * @param messages in the chat
     * @param sentMessage sent message from the current user
     */
    void learn(List<Message> messages, Message sentMessage);
}
