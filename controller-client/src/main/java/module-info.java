module controller.client {
    requires service.client;
    requires model.server;
    requires java.rmi;
    exports com.chat.client.controller.client.user;
    exports com.chat.client.controller.client.user.login;
}