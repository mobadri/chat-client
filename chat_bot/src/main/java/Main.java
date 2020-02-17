import chat_bot.ChatBotInterface;
import chat_bot.factory.ChatBotFactory;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        ChatBotInterface chatBotInterface = ChatBotFactory.createChatBotInstance();

        while (true){

            System.out.println("message");
            String message = scanner.nextLine();
            System.out.println(chatBotInterface.getMessage(message));
        }
    }
}
