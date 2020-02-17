module chat.bot {
    requires java.xml;
    exports chat_bot;
    opens chat_bot;
    exports chat_bot.factory;
}