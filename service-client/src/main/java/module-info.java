module service.client {
    exports com.chat.client.service.client.factory;
    exports com.chat.client.service.client.user;
    exports com.chat.client.service.client.notification;
    exports com.chat.client.service.client.message;
    exports com.chat.client.service.client.chat;
    exports com.chat.client.service.client.user.validation;
    requires repository.client;
    requires model.server;
    requires java.rmi;
    requires network.client;
    requires service.server;
    opens com.chat.client.service.client.factory;
    opens com.chat.client.service.client.user;
    opens com.chat.client.service.client.user.impl;
    opens com.chat.client.service.client.message;
    opens com.chat.client.service.client.message.impl;
    opens com.chat.client.service.client.chat;
    opens com.chat.client.service.client.chat.impl;
    opens com.chat.client.service.client.notification;
    opens com.chat.client.service.client.notification.impl;
}