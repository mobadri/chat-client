package com.chat.client.view.client.chat;

import com.chat.client.controller.client.chatGroup.ChatGroupInterface;
import com.chat.client.controller.client.fileTransfer.FileTranseferController;
import com.chat.client.controller.client.fileTransfer.FileTranseferControllerImpl;
import com.chat.server.model.chat.ChatGroup;
import com.chat.server.model.user.User;
import com.healthmarketscience.rmiio.RemoteInputStreamServer;
import com.healthmarketscience.rmiio.SimpleRemoteInputStream;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.*;
import java.net.URL;
import java.rmi.RemoteException;
import java.util.ResourceBundle;

public class TransferFileController implements Initializable {
    FileTranseferController fileTranseferController;
    @FXML
    private Label pathOfFile;
    private String nameOfFile;
    private Stage stage;

    private User currentUser;
    private ChatGroup currentChatGroup;

    public TransferFileController() {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    @FXML
    public void handleSendFile(MouseEvent mouseEvent) {
        System.out.println("currentUser" + currentUser);
        System.out.println("currentChatGroup" + currentChatGroup.getName());

        RemoteInputStreamServer istream = null;
        try {
            BufferedInputStream bufferedInputStream = new BufferedInputStream
                    (new FileInputStream(new File(pathOfFile.getText())));
            System.out.println("handleSendFile pathOfFile is supposednot to be null : " + pathOfFile.getText());
            istream = new SimpleRemoteInputStream(bufferedInputStream);
            System.out.println(istream);
            System.out.println("handleSendFile istream is supposednot to be null : " + istream);
            System.out.println("fxml");

            fileTranseferController.sendFile(nameOfFile, istream);
            stage.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {

            if (istream != null) istream.close();
        }
    }

    public void setPathOfFile(String pathOfFile) {
        this.pathOfFile.setText(pathOfFile);
    }

    public void setNameOfFile(String nameOfFile) {
        this.nameOfFile = nameOfFile;
    }


    public void setCurrentUser(User user) {
        this.currentUser = user;
        fileTranseferController.setCurrentUser(currentUser);
    }

    public void setCurrentChatGroup(ChatGroup currentChatGroup) {
        this.currentChatGroup = currentChatGroup;
        fileTranseferController.setChatGroup(currentChatGroup);
    }

    public void setFileTranseferController(FileTranseferController fileTranseferController) {
        this.fileTranseferController = fileTranseferController;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }
}
