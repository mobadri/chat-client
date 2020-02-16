package chat_bot.factory;

import chat_bot.ChatBotInterface;
import chat_bot.impl.ChatBot;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

public class ChatBoxFactory {

    public static ChatBotInterface createChatBotInstance(){

        try {
            return ChatBot.getInstance();
        } catch (IOException | SAXException | ParserConfigurationException e) {
            e.printStackTrace();
        }
        return null;
    }
}
