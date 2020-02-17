module view.client {
    requires model.server;
    requires javafx.controls;
    requires controller.client;
    requires javafx.fxml;
    requires javafx.base;
    requires javafx.graphics;
    requires javafx.web;

    requires com.jfoenix;
    requires fontawesomefx;
    requires org.controlsfx.controls;
    requires service.client;
    requires java.rmi;
    requires network.client;
    requires org.kordamp.ikonli.octicons;
    requires org.kordamp.ikonli.javafx;
    opens com.chat.client.view.client;
    opens com.chat.client.view.client.login;
    opens com.chat.client.view.client.chat;
    opens com.chat.client.view.client.user;
    opens com.chat.client.view.client.friend;


}