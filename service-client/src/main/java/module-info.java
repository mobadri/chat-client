module service.client {
    exports com.chat.client.service.client.factory;
    exports com.chat.client.service.client.user;
    exports com.chat.client.service.client.notification;
    exports com.chat.client.service.client.message;
    exports com.chat.client.service.client.chat;
    requires repository.client;
    requires model.server;
    opens com.chat.client.service.client.factory;
    opens com.chat.client.service.client.user;

}