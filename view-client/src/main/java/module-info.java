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
//    requires network.client;
    opens com.chat.client.view.client;
    opens com.chat.client.view.client.login;
    opens com.chat.client.view.client.chat;


}