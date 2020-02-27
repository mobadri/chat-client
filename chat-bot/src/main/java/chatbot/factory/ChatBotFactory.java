package chatbot.factory;

import chatbot.ChatBotInterface;
import chatbot.impl.ChatBot;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.IOException;

public class ChatBotFactory {

    public static ChatBotInterface createChatBotInstance(String userPhone) {

        try {
            return ChatBot.getInstance(userPhone);
        } catch (IOException | SAXException | ParserConfigurationException | TransformerException e) {
            e.printStackTrace();
        }
        return null;
    }
}
