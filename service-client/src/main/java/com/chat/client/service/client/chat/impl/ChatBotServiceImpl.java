package com.chat.client.service.client.chat.impl;


import chatbot.ChatBotInterface;
import chatbot.factory.ChatBotFactory;
import com.chat.client.service.client.chat.ChatBotService;
import com.chat.server.model.chat.Message;

import java.util.List;

public class ChatBotServiceImpl implements ChatBotService {

    private static ChatBotServiceImpl instance;
    private ChatBotInterface chatBot;

    public ChatBotServiceImpl(String userPhone) {
        chatBot = ChatBotFactory.createChatBotInstance(userPhone);
    }

    public static synchronized ChatBotServiceImpl createChatBotServiceInstance(String userPhone) {
        if (instance == null) {
            instance = new ChatBotServiceImpl(userPhone);
        }
        return instance;
    }

    @Override
    public String getMessage(String message) {
        return chatBot.getMessage(message);
    }

    @Override
    public void learn(List<Message> messages, Message sentMessage) {
        if(messages.size() > 0 && messages.get(messages.size() - 1).getUserFrom().getId() != sentMessage.getUserFrom().getId()){
            chatBot.learn(messages.get(messages.size() - 1).getMessage(), sentMessage.getMessage());
        }
    }
}
