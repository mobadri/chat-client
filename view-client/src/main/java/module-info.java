module view.client {
    requires model.server;
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.base;
    requires javafx.graphics;
    requires com.jfoenix;
    requires fontawesomefx;
    opens com.chat.client.view.client;
}