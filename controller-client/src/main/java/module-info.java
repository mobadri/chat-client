module controller.client {
    requires service.client;
    requires serviceclientcallback;
    requires model.server;
    requires java.rmi;
    requires java.xml;
    requires rmiio;
    exports com.chat.client.controller.client.user;
    exports com.chat.client.controller.client.user.login;
    exports com.chat.client.controller.client.chatGroup;
    exports com.chat.client.controller.client.pushNotifications;
    exports com.chat.client.controller.client.message;
    exports com.chat.client.controller.client.fileTransfer;
}