module network.client {
    requires java.rmi;
    requires model.server;
    requires service.server;
    opens com.chat.client.network.client.factory;
    opens com.chat.client.network.client.chat;
    opens com.chat.client.network.client.user;
    exports com.chat.client.network.client.factory;
    exports com.chat.client.network.client.chat;
    exports com.chat.client.network.client.user;
    exports com.chat.client.network.client.user.impl;
}
