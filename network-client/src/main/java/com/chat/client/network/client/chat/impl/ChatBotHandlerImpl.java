package com.chat.client.network.client.chat.impl;

import chat_bot.ChatBotInterface;
import chat_bot.factory.ChatBotFactory;
import com.chat.client.network.client.chat.ChatBotHandler;

public class ChatBotHandlerImpl implements ChatBotHandler {

    private ChatBotInterface chatBot;

    public ChatBotHandlerImpl() {
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
