module repository.client {
    exports com.chat.client.repository.client.message;
    exports com.chat.client.repository.client.factory;
    exports com.chat.client.repository.client.chat;
    exports com.chat.client.repository.client.notification;
    exports com.chat.client.repository.client.user;
    //requires network.client;
    requires model.server;
}