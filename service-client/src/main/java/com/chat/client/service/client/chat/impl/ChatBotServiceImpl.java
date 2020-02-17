package com.chat.client.service.client.chat.impl;


import chatbot.ChatBotInterface;
import chatbot.factory.ChatBotFactory;
import com.chat.client.service.client.chat.ChatBotService;

public class ChatBotServiceImpl implements ChatBotService {

    private ChatBotInterface chatBot;

    public ChatBotServiceImpl() {
        chatBot = ChatBotFactory.createChatBotInstance();
    }

    @Override
    public String getMessage(String message) {
        return chatBot.getMessage(message);
    }

    @Override
    public void learn(String message, String response) {
        chatBot.learn(message, response);
    }
}
