module service.client {
    requires model.server;
    opens com.chat.client.service.client.factory;
    opens com.chat.client.service.client.user;

}