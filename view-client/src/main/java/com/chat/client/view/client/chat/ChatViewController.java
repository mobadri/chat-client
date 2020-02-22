package com.chat.client.view.client.chat;


import com.chat.client.controller.client.chatGroup.ChatGroupInterface;
import com.chat.server.model.chat.ChatGroup;
import com.chat.server.model.chat.Message;
import com.chat.server.model.chat.Style;
import com.chat.server.model.user.User;
import com.jfoenix.controls.*;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.Window;
import org.controlsfx.dialog.FontSelectorDialog;

import java.io.File;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.StringTokenizer;
import java.util.stream.Collectors;

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
    private Style style;

    @FXML
    private VBox messageBox;

    @FXML
    private ComboBox sizeComboBox;

    @FXML
    private ComboBox fontComboBox;

    @FXML
    private ColorPicker fontColorPicker;

    private Color currentColor = Color.BLACK;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        new Thread(() -> {
            Platform.runLater(() -> {
                loadFontFamily();
                loadSize();
            });
        }).start();

    }

    public ChatViewController() {
        style = new Style();
        style.setFontName("Arial");
        style.setFontFamily("Arial");
        style.setBackground("white");
        style.setFontColor("black");
        style.setFontSize(14);
        style.setBold(false);
        style.setItalic(false);
        style.setUnderline(false);

    }

    @FXML
    private void sendMessageAction(ActionEvent actionEvent) {
        Message message = createMessage();
        sendMessage(message);
    }

    private Message createMessage() {
        String messageContentText = messageContent.getText();
        Message message = new Message();
        message.setMessage(messageContentText);
        message.setStyle(style);
        message.setChatGroup(currentChatGroup);
        message.setTimestamp(LocalDate.now());
        message.setUserFrom(currentUser);
        return message;
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

    @Override
    public void receiveMessage(Message message) {
        showReceivedMessage(message);
    }

    @Override
    public void setChatGroup(ChatGroup chatGroup) {
        this.currentChatGroup = chatGroup;
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
        text.setStyle(message.getStyle().toString());
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
        pane.setStyle(" -fx-background-radius: 10px;" + message.getStyle().toString());

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


    @FXML
    public void openFile(MouseEvent mouseEvent) {

    }


    public void handleSendingFile(MouseEvent mouseEvent) {
        FileChooser fileChooser = new FileChooser();
        File file = fileChooser.showOpenDialog(new Stage());
        System.out.println(file.getPath());

    }


    private void loadSize() {
        List<Integer> sizes = new ArrayList<>();
        for (int i = 1; i <= 72; i += 2) {
            sizes.add(i);
        }

        sizeComboBox.setItems(FXCollections.observableList(sizes));
    }

    private void loadFontFamily() {
        List<String> fontFamilies = Font.getFamilies().parallelStream().sorted().
                collect(Collectors.toList());
        fontComboBox.setItems(FXCollections.observableList(fontFamilies));

        System.out.println(fontFamilies);
    }


    public void onClickBoldBuuton(ActionEvent actionEvent) {

        style.setBold(!style.isBold());
        messageContent.setStyle(style.toString());
    }

    public void onClickItalicButton(ActionEvent actionEvent) {
        style.setItalic(!style.isItalic());
        messageContent.setStyle(style.toString());

    }

    @Override
    public void unregisterService() {
        chatGroupInterface.unregisterService();
    }


    public void onClickFontComboBox(ActionEvent actionEvent) {
        style.setFontName(fontComboBox.getValue().toString());
        messageContent.setStyle(style.toString());
        System.out.println(style.getFontName());
    }

    public void onClickSizeComboBox(ActionEvent actionEvent) {
        style.setFontSize(Integer.parseInt(sizeComboBox.getValue().toString()));
        messageContent.setStyle(style.toString());
        System.out.println(style.getFontSize());

    }


    public void onClickColorPicker(ActionEvent actionEvent) {
        style.setFontColor(format(fontColorPicker.getValue()));
        messageContent.setStyle(style.toString());

    }
}