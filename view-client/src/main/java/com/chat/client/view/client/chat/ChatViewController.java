package com.chat.client.view.client.chat;


import com.chat.server.model.user.User;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.web.HTMLEditor;
import javafx.scene.web.WebView;

import java.net.URL;
import java.util.ResourceBundle;

public class ChatViewController implements Initializable {


    @FXML
    private Label userName ;
    @FXML
    private HTMLEditor htmlEditor;
    private User user ;

    @FXML
    private VBox messageBox;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    @FXML
    private void sendMessage() {
        WebView webView = new WebView();
        String htmlText = htmlEditor.getHtmlText();
        System.out.println(htmlText);
//        webView.getEngine().loadContent();

        HBox hBox = new HBox();
        hBox.getChildren().add(webView);

        messageBox.getChildren().add(hBox);
    }

    public void setUser(User user) {
        System.out.println("set user data");
        this.user = user;
        Platform.runLater(()->
            userName.setText(user.getFirstName() + " " +user.getLastName())
        );

    }
}
