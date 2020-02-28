package com.chat.client.view.client.chat;


import com.chat.client.controller.client.chatGroup.ChatGroupInterface;
import com.chat.client.controller.client.fileTransfer.FileTranseferController;
import com.chat.client.controller.client.fileTransfer.FileTranseferControllerImpl;
import com.chat.client.controller.client.message.MessageControllerImpl;
import com.chat.client.view.client.chat.render.MessageCellRenderer;
import com.chat.server.model.chat.ChatGroup;
import com.chat.server.model.chat.Message;
import com.chat.server.model.chat.Style;
import com.chat.server.model.user.User;
import com.jfoenix.controls.*;
import javafx.application.Platform;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.controlsfx.dialog.FontSelectorDialog;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.rmi.RemoteException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.StringTokenizer;

public class ChatViewController implements Initializable, ChatGroupInterface {

    ChatGroupInterface chatGroupInterface;
    //----------------------------------------------------------------------
    //---------------------------view section ------------------------------
    //--------------------------------------- ------------------------------
    @FXML
    private JFXComboBox<Integer> sizeComboBox;
    @FXML
    private JFXButton boldButon;
    @FXML
    private JFXButton italicButton;
    @FXML
    private JFXColorPicker fontColorPicker;
    @FXML
    private JFXListView messageListView;
    @FXML
    private JFXTextArea messageContent;
    @FXML
    private ColorPicker backgroundColorPicker;
    @FXML
    private Button chooseFontButton;
    @FXML
    private Label chatGroupName;
    @FXML
    private ImageView addnewfriend;
    @FXML
    private JFXCheckBox chatBot;

    //----------------------------------------------------------------------
    //---------------------------data section ------------------------------
    //--------------------------------------- ------------------------------
    private ObservableList<Message> messageObservableList = FXCollections.observableArrayList();
    private ListProperty<Message> messageListProperty = new SimpleListProperty<>();
    private MessageControllerImpl messageController = new MessageControllerImpl();
    private User currentUser;
    private ChatGroup currentChatGroup;
    private Style defualtStyle;
    private Color currentColor = Color.BLACK;
    private Color bgColor = Color.WHITE;
    private FileTranseferController fileTranseferController;

    private boolean isChatBotEnabled;

    public ChatViewController() {
        setDefaultStyle();

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        new Thread(() -> {
            Platform.runLater(() -> {
//                loadFontFamily();
                loadSize();
            });
        }).start();

        messageListProperty.set(messageObservableList);

        messageListView.itemsProperty().bindBidirectional(messageListProperty);
        messageListView.setItems(messageListProperty);
        messageListView.setCellFactory(new MessageCellRenderer());
        messageContent.setStyle(defualtStyle.toString());

    }

    public void enableChatBot(ActionEvent actionEvent) {
        isChatBotEnabled = chatBot.isSelected();
    }

    @FXML
    private void sendMessageAction(ActionEvent actionEvent) {
        Message message = createMessage();
        sendMessage(message, isChatBotEnabled);
    }

    private Message createMessage() {
        String messageContentText = messageContent.getText();
        Message message = new Message();
        message.setMessage(messageContentText);
        message.setStyle(defualtStyle);
        message.setChatGroup(currentChatGroup);
        message.setTimestamp(LocalDate.now());
        message.setUserFrom(currentUser);
        return message;
    }

    @Override
    public void sendMessage(Message message, boolean isChatBotEnabled) {
        chatGroupInterface.sendMessage(message, isChatBotEnabled);
    }

    @Override
    public void receiveMessage(Message message) {
        Platform.runLater(() -> {
            showReceivedMessage(message);
        });

        if (isChatBotEnabled)
            getChatBotResponse(message);
        currentChatGroup.getMessages().add(message);
    }

    @Override
    public void getChatBotResponse(Message receivedMessage) {
        chatGroupInterface.getChatBotResponse(receivedMessage);
    }

    @Override
    public Message createMessage(String messageContent) {
        return null;
    }

//    @Override
//    public void setChatGroup(ChatGroup chatGroup) {
//        this.currentChatGroup = chatGroup;
//    }

//    @FXML
//    private void changeTextColor(ActionEvent actionEvent) {
//        Color color = colorChooser.getValue();
//        System.out.println(color.toString());
//        currentColor = color;
//        messageContent.setStyle("-fx-text-inner-color :" + format(color) + ";");
//
//    }

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

    public void showReceivedMessage(Message message) {
        messageObservableList.add(message);
    }


    @FXML
    private void saveMessages(MouseEvent mouseEvent) {
        messageController.saveMessages(currentChatGroup.getMessages(), "messages.xml");
    }

    @FXML
    private void onClickFontColorPicker(ActionEvent actionEvent) {
        Color color = fontColorPicker.getValue();
        currentColor = color;
//        System.out.println(color.toString());
        defualtStyle.setFontColor(format(color));
        System.out.println(defualtStyle.toString());
        messageContent.setStyle(defualtStyle.toString());
    }

    @FXML
    private void onClickBackgroundColorPicker(ActionEvent actionEvent) {
        Color color = backgroundColorPicker.getValue();
        bgColor = color;
        defualtStyle.setBackground(format(color));
        messageContent.setStyle(defualtStyle.toString());
    }

    @FXML
    public void addnewfriendAction(MouseEvent mouseEvent) {
        try {
            Stage stage = new Stage();
            FXMLLoader loader =
                    new FXMLLoader(getClass().getResource("/templates/chat/addfriendstochat.fxml"));
            Parent root = loader.load();
            AddFriendToChatGroup controller = loader.getController();
            controller.setCurrentUser(currentUser);
            System.err.println("Chat View controller current user " + currentUser.getFriends().size());
            System.err.println("Chat View controller chat group user " + currentChatGroup.getUsers().size());
            controller.setGroupChat(currentChatGroup);

            stage.setScene(new Scene(root));
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void handleSendingFile(MouseEvent mouseEvent) {
        FileChooser fileChooser = new FileChooser();
        File file = fileChooser.showOpenDialog(new Stage());
        if (file != null) {
            loadFxmlFileTransefer(file);
        }
    }

    public void loadFxmlFileTransefer(File file) {
        try {
            FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/templates/user/TransferFile.fxml"));
            Parent root = loader.load();
            FileTransferControllerFXML transferControllerFXML = loader.getController();
            /***********************************************************************************/
            fileTranseferController.setCurrentUser(currentUser);
            fileTranseferController.setChatGroup(currentChatGroup);
            transferControllerFXML.setFileTranseferController(fileTranseferController);


            /*****************************************************************************/

            transferControllerFXML.setNameOfFile(file.getName());
            transferControllerFXML.setPathOfFile(file.getPath());
            System.out.println(file.getPath());
            transferControllerFXML.setCurrentUser(currentUser);
            transferControllerFXML.setCurrentChatGroup(currentChatGroup);
            System.out.println("currentChatGroup" + currentChatGroup);
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.initStyle(StageStyle.TRANSPARENT);
            stage.setScene(scene);
            transferControllerFXML.setStage(stage);
            transferControllerFXML.setFileTransferControllerFXML(transferControllerFXML);
            System.out.println("transferControllerFXML in chat View "+transferControllerFXML);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadSize() {
        List<Integer> sizes = new ArrayList<>();
        for (int i = 1; i <= 72; i += 2) {
            sizes.add(i);
        }

        sizeComboBox.setItems(FXCollections.observableList(sizes));
    }

//    private void loadFontFamily() {
//        List<String> fontFamilies = Font.getFamilies().parallelStream().sorted().
//                collect(Collectors.toList());
//        fontComboBox.setItems(FXCollections.observableList(fontFamilies));
//    }


    public void onClickBoldBuuton(ActionEvent actionEvent) {
        defualtStyle.setBold(!defualtStyle.isBold());
        messageContent.setStyle(defualtStyle.toString());
    }

    public void onClickItalicButton(ActionEvent actionEvent) {
        defualtStyle.setItalic(!defualtStyle.isItalic());
        messageContent.setStyle(defualtStyle.toString());

    }

    @Override
    public void unregisterService() {
        chatGroupInterface.unregisterService();
    }

//
//    public void onClickFontComboBox(ActionEvent actionEvent) {
//        defualtStyle.setFontName(fontComboBox.getValue().toString());
//        messageContent.setStyle(defualtStyle.toString());
//        System.out.println(defualtStyle.getFontName());
//    }

    public void onClickSizeComboBox(ActionEvent actionEvent) {
        defualtStyle.setFontSize(sizeComboBox.getValue());
        messageContent.setStyle(defualtStyle.toString());
        System.out.println(defualtStyle.getFontSize());
    }

    @FXML
    private void onClickColorPicker(ActionEvent actionEvent) {
        defualtStyle.setFontColor(format(fontColorPicker.getValue()));
        messageContent.setStyle(defualtStyle.toString());
        System.out.println(format(fontColorPicker.getValue()));
    }

    @FXML
    public void AddUserToGroup(ActionEvent actionEvent) {
        Parent root;
        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/templates/chat/Chat-group-users.fxml"));
            root = loader.load();
            ChatGroupUsers chatGroupUsers = loader.getController();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
            System.out.println("kkkkkkkkkkkkkk");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //-----------------------------------------------------------------------
    //----------------------------view section ------------------------------
    //-------------------------------------------------------------------------
    private void setDefaultStyle() {
        defualtStyle = new Style();
        defualtStyle.setFontName("Arial");
        defualtStyle.setFontFamily("Arial");
        defualtStyle.setBackground("white");
        defualtStyle.setFontColor("black");
        defualtStyle.setFontSize(16);
        defualtStyle.setBold(false);
        defualtStyle.setItalic(false);
        defualtStyle.setUnderline(false);
        defualtStyle.setFontColor(format(currentColor));
        defualtStyle.setBackground(format(bgColor));
    }


    //-------------------------------------------------------------------------
    //----------------------------data section ------------------------------
    //-------------------------------------------------------------------------
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
        defualtStyle.setFontColor(format(color));
        defualtStyle.setFontName(name);
        defualtStyle.setFontFamily(family);
        defualtStyle.setFontSize(Integer.parseInt(size));
        defualtStyle.setBold(style.contains("bold"));
        defualtStyle.setItalic(style.contains("Italic"));
        defualtStyle.setUnderline(style.contains("underline"));

        stringBuilder.append("-fx-fill:" + format(color) + ";");
        stringBuilder.append("-fx-font-name:" + name + ";");
        stringBuilder.append("-fx-font-family:" + family + ";");
        stringBuilder.append("-fx-font-size:" + size + ";");
        stringBuilder.append("-fx-font-weight:" + style + ";");

        return stringBuilder.toString();
    }
    //-------------------------------------------------------------------------
    //----------------------------setter section ------------------------------
    //-------------------------------------------------------------------------

    public void setUser(User user) {
        this.currentUser = user;
        try {
            fileTranseferController = new FileTranseferControllerImpl();
            System.out.println("ChatViewController created");
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        fileTranseferController.setCurrentUser(user);
    }

    @Override
    public void setChatGroup(ChatGroup chatGroup) {
        this.currentChatGroup = chatGroup;
        chatGroupName.setText(currentChatGroup.getName());
        fileTranseferController.setChatGroup(currentChatGroup);
    }


    public void setChatGroupInterface(ChatGroupInterface chatGroupInterface) {
        this.chatGroupInterface = chatGroupInterface;
    }

    public FileTranseferController getFileTranseferController() {
        return fileTranseferController;
    }
}