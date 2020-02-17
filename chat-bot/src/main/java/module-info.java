module chat.bot {
    requires java.xml;
    opens chatbot.factory;
    exports chatbot.factory;
    opens chatbot;
    exports chatbot;
}