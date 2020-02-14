package com.chat.client.view.client.chat;


import com.chat.client.controller.client.chatGroup.ChatGroupInterface;
import com.chat.server.model.chat.ChatGroup;
import com.chat.server.model.chat.Message;
import com.chat.server.model.chat.Notification;
import com.chat.server.model.chat.NotificationType;
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

public class ChatViewController implements Initializable, ChatGroupInterface {
    ChatGroupInterface chatGroupInterface;

    @FXML
    private Label userName;
    @FXML
    private HTMLEditor htmleditor;
    private User currentUser;
    private ChatGroup currentChatGroup;
    @FXML
    private VBox messageBox;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    @FXML
    private void sendMessage() {
        WebView webView = new WebView();
        String htmlText = htmleditor.getHtmlText();
        System.out.println(htmlText);
//        webView.getEngine().loadContent();

        HBox hBox = new HBox();
        hBox.getChildren().add(webView);
        Message message = new Message();
        message.setMessage(htmlText);
        Notification notification = new Notification();
        notification.setNotificationType(NotificationType.MESSAGE_RECEIVED);
        message.setUserFrom(currentUser);
        message.setChatGroup(currentChatGroup);
        System.out.println(message);
        sendMessage(message);


        receiveMessage(message);
//        messageBox.getChildren().add(hBox);
    }

    public void setUser(User user) {
        System.out.println("set user data");
        this.currentUser = user;
        Platform.runLater(() ->
                userName.setText(user.getFirstName() + " " + user.getLastName())
        );

    }

    public void setChatGroupInterface(ChatGroupInterface chatGroupInterface) {
        this.chatGroupInterface = chatGroupInterface;
    }

    @Override
    public void sendMessage(Message message) {
        chatGroupInterface.sendMessage(message);
    }

    public void setCurrentChatGroup(ChatGroup currentChatGroup) {
        this.currentChatGroup = currentChatGroup;
    }

    @Override
    public void receiveMessage(Message message) {
        System.err.println("received message");
        System.out.println(message);
    }

}
