package chatbot.factory;

import chatbot.ChatBotInterface;
import chatbot.impl.ChatBot;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

public class ChatBotFactory {

    public static ChatBotInterface createChatBotInstance() {

        try {
            return ChatBot.getInstance();
        } catch (IOException | SAXException | ParserConfigurationException e) {
            e.printStackTrace();
        }
        return null;
    }
}
