package com.chat.client.network.client.chat;

public interface ChatBotHandler {

    public String getMessage(String message);

    public void learn(String message, String response);
}
