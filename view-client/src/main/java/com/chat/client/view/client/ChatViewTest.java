package com.chat.client.view.client;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ChatViewTest extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(this.getClass().getResource("/templates/chatproperties.fxml"));
        stage.setTitle("Chat Room");
        stage.setScene(new Scene(root));
        stage.show();
    }
}
