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
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;

import java.io.*;
import java.net.URL;
import java.rmi.RemoteException;
import java.util.ResourceBundle;

public class TransferFileController implements Initializable {

    FileTranseferController fileTranseferController = new FileTranseferControllerImpl();

    @FXML
    private Label nameOfFile;

    private User currentUser;
    private ChatGroup currentChatGroup;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }


    @FXML
    public void handleSendFile(MouseEvent mouseEvent) {

        RemoteInputStreamServer istream = null;
        try {
            BufferedInputStream bufferedInputStream = new BufferedInputStream
                    (new FileInputStream(new File(nameOfFile.getText())));
            System.out.println(nameOfFile.getText());
            istream = new SimpleRemoteInputStream(bufferedInputStream);
            System.out.println(istream);
            System.out.println("fxml");

            fileTranseferController.sendFile(nameOfFile.getText(), istream.export());

        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {

            if (istream != null) istream.close();
        }
    }

    public void setNameOfFile(String nameOfFile) {
        this.nameOfFile.setText(nameOfFile);
    }
}
