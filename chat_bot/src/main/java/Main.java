import chat_bot.ChatBotInterface;
import chat_bot.factory.ChatBoxFactory;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        ChatBotInterface chatBotInterface = ChatBoxFactory.createChatBotInstance();

        while (true){

            System.out.println("message");
            String message = scanner.nextLine();
            System.out.println(chatBotInterface.getMessage(message));
        }
    }
}
