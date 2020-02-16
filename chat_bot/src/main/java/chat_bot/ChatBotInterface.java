package chat_bot;


public interface ChatBotInterface {

    public String getMessage(String message);

    public void learn(String message, String respond);
}
