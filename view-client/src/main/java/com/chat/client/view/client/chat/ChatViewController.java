package com.chat.client.view.client.chat;


import com.chat.client.controller.client.chatGroup.ChatGroupInterface;
import com.chat.server.model.chat.ChatGroup;
import com.chat.server.model.chat.Message;
import com.chat.server.model.user.User;
import com.jfoenix.controls.JFXTextArea;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import org.controlsfx.dialog.FontSelectorDialog;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.StringTokenizer;

public class ChatViewController implements Initializable, ChatGroupInterface {
    ChatGroupInterface chatGroupInterface;

    @FXML
    private Label userName;
    @FXML
    private JFXTextArea messageContent;
    @FXML
    private ColorPicker colorChooser;
    @FXML
    private Button chooseFontButton;
    private User currentUser;
    private ChatGroup currentChatGroup;
    @FXML
    private VBox messageBox;

    private Color currentColor;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    @FXML
    private void sendMessage() {
        String messageContentText = messageContent.getText();
//        System.out.println(messageContentText);
//        System.out.println(messageContent.getStyle());
        Message message = new Message();
        message.setMessage(messageContentText);
        message.setStyle(messageContent.getStyle());
        System.out.println("" + messageContent.getFont());
//        new Font("" + messageContent.getFont(), 20);
//        getFont("" + messageContent.getFont());
        message.setStyle(getFont("" + messageContent.getFont(), currentColor));
        message.setUserFrom(currentUser);
        showReceivedMessage(message);
//
//        HBox hBox = new HBox();
//        hBox.getChildren().add(webView);
//        Message message = new Message();
//        message.setMessage(messageContentText);
//        Notification notification = new Notification();
//        notification.setNotificationType(NotificationType.MESSAGE_RECEIVED);
//        message.setUserFrom(currentUser);
//        message.setChatGroup(currentChatGroup);
//        System.out.println(message);
//        sendMessage(message);


//        receiveMessage(message);
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

    @FXML
    private void changeTextColor(ActionEvent actionEvent) {
        Color color = colorChooser.getValue();
        System.out.println(color.toString());
        currentColor = color;
        messageContent.setStyle("-fx-text-inner-color :" + format(color) + ";");

    }

    private String format(Color c) {
        int r = (int) (255 * c.getRed());
        int g = (int) (255 * c.getGreen());
        int b = (int) (255 * c.getBlue());
        return String.format("#%02x%02x%02x", r, g, b);
    }

    @FXML
    private void onFontChooser(ActionEvent actionEvent) {
        Dialog<Font> fontSelector = new FontSelectorDialog(messageContent.getFont());
        fontSelector.showAndWait().ifPresent(messageContent::setFont);
    }


    private String getFont(String messageFont, Color color) {
        messageFont = messageFont.substring(5, messageFont.length() - 1);

        Font font = new Font(12);
        StringTokenizer stringTokenizer = new StringTokenizer(messageFont, ",");
        String name = stringTokenizer.nextToken().split("=")[1];
        String family = stringTokenizer.nextToken().split("=")[1];
        String style = stringTokenizer.nextToken().split("=")[1];
        String size = stringTokenizer.nextToken().split("=")[1];
        System.out.println(name + ", " + family + ", " + style + ", " + size);

        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append("-fx-fill:" + format(color) + ";");
        stringBuilder.append("-fx-font-name:" + name + ";");
        stringBuilder.append("-fx-font-family:" + family + ";");
        stringBuilder.append("-fx-font-size:" + size + ";");
        stringBuilder.append("-fx-font-weight:" + style + ";");

        return stringBuilder.toString();
    }

//    public void showSentMessage(User user) {
//
//        Text text = new Text(user.getMessage());
//        text.setTextAlignment(TextAlignment.JUSTIFY);
//        text.setStyle("-fx-fill: white; -fx-font-size: 15;");
//
//        ImageView imageView = getImageView(user);
//
//        Label label = new Label(user.getUserName(), imageView);
//        label.setContentDisplay(ContentDisplay.BOTTOM);
//
//        HBox hBox = new HBox(10);
//        hBox.setPadding(new Insets(5, 10, 2, 10));
//
//        FlowPane pane = new FlowPane();
//        pane.getChildren().add(text);
//        pane.setAlignment(Pos.BASELINE_RIGHT);
//        pane.setHgap(15);
//        pane.setPrefHeight(45);
//        pane.setStyle(" -fx-background-radius: 10px; -fx-background-color:#7D1B7E ;");
//
//        hBox.getChildren().addAll(pane, label);
//        hBox.setAlignment(Pos.BASELINE_RIGHT);
//
//        Platform.runLater(() -> {
//            vBox.getChildren().add(hBox);
//        });
//    }

    public void showReceivedMessage(Message message) {

        Text text = new Text(message.getMessage());
        text.setTextAlignment(TextAlignment.JUSTIFY);
//        text.setStyle("-fx-fill: black; -fx-font-size: 15;");
        text.setStyle(message.getStyle());
        System.out.println(message.getStyle());

//        ImageView imageView = getImageView(message.get);

        Label label = new Label(message.getUserFrom().getFirstName()
                + " " + message.getUserFrom().getLastName());
        label.setContentDisplay(ContentDisplay.BOTTOM);

        HBox hBox = new HBox(10);
        hBox.setPadding(new Insets(5, 10, 2, 10));

        FlowPane pane = new FlowPane();
        pane.getChildren().add(text);
        pane.setAlignment(Pos.BASELINE_LEFT);
        pane.setHgap(15);
        pane.setPrefHeight(45);
        pane.setStyle(" -fx-background-radius: 10px; -fx-background-color: #F0A9DB;");

        hBox.getChildren().addAll(label, pane);
        hBox.setAlignment(Pos.BASELINE_LEFT);

        Platform.runLater(() -> {
            messageBox.getChildren().add(hBox);
        });
    }

//    private ImageView getImageView(User user) {
//
//        ImageView imageView = null;
//        try {
//            imageView = new ImageView(new Image(new FileInputStream(user.getPhotoPath())));
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        }
//        imageView.setFitWidth(20);
//        imageView.setFitHeight(20);
//        imageView.setPreserveRatio(true);
//        return imageView;
//    }


}
