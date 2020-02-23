module controller.client {
    requires service.client;
    requires model.server;
    requires java.rmi;
    requires java.xml;
    requires serviceclientcallback;
    requires java.xml.bind;
    exports com.chat.client.controller.client.user;
    exports com.chat.client.controller.client.user.login;
    exports com.chat.client.controller.client.chatGroup;
    exports com.chat.client.controller.client.pushNotifications;
    exports com.chat.client.controller.client.message;
}