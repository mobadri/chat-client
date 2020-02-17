package com.chat.client.service.client.chat;

public interface ChatBotService {

    public String getMessage(String message);

    public void learn(String message, String response);
}
