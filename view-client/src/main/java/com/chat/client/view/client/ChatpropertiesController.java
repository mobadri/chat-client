/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.chat.client.view.client;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * FXML Controller class
 *
 * @author Yasmeen
 */
public class ChatpropertiesController implements Initializable {

    @FXML
    private VBox vBoxNotifications;
    @FXML
    private ImageView notificationsIcon;
    @FXML
    private Label notificationText;
    @FXML
    private VBox vBoxChats;
    @FXML
    private ImageView chatIcon;
    @FXML
    private Label chatText;
    @FXML
    private VBox vBoxContacts;
    @FXML
    private ImageView contactIcon;
    @FXML
    private Label contactText;
    @FXML
    private AnchorPane addNewContactPane;
    @FXML
    private Button newContactButton;
    @FXML
    private AnchorPane newChatPane;
    @FXML
    private Button newChatButton;
    @FXML
    private Label namePerson;
    @FXML
    private Circle circleImageView;
    @FXML
    private TextField peopleSearch;
    boolean isCheckedForChats = false;
    boolean isCheckedForContacts = false;
    boolean isCheckedForNotifications = false;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Image image = new Image(getClass().getResource("/static/images/followers.png").toExternalForm());

        //imgPerson.setImage(image);
        circleImageView.setFill(new ImagePattern(image));
        newChatPane.setVisible(true);
        changeTheTextColor(chatText);
        chatIcon.setImage(new Image(getClass().getResource("/static/images/chatBlue.png").toExternalForm()));
        vBoxChats.setOnMouseClicked((event) -> {
            contactIcon.setImage(new Image(getClass().getResource("/static/images/contacts.png").toExternalForm()));
            notificationsIcon.setImage(new Image(getClass().getResource("/static/images/bell.png").toExternalForm()));

            isCheckedForContacts = false;
            isCheckedForNotifications = false;
            isCheckedForChats = true;
            chatIcon.setImage(new Image(getClass().getResource("/static/images/chatBlue.png").toExternalForm()));
            removeTextBackground(contactText);
            removeTextBackground(notificationText);

            if (isCheckedForChats) {
                newChatPane.setVisible(true);
                changeTheTextColor(chatText);
            }

        });
        vBoxContacts.setOnMouseClicked((event) -> {

            notificationsIcon.setImage(new Image(getClass().getResource("/static/images/bell.png").toExternalForm()));
            chatIcon.setImage(new Image(getClass().getResource("/static/images/chat2.png").toExternalForm()));

            removeTextBackground(chatText);
            removeTextBackground(notificationText);
            isCheckedForChats = false;
            isCheckedForNotifications = false;
            isCheckedForContacts = true;
            contactIcon.setImage(new Image(getClass().getResource("/static/images/contactBlue.png").toExternalForm()));
            if (isCheckedForContacts) {
                addNewContactPane.setVisible(true);
                newChatPane.setVisible(false);
                changeTheTextColor(contactText);
            }

        });
        vBoxNotifications.setOnMouseClicked((event) -> {
            isCheckedForChats = false;
            isCheckedForContacts = false;
            isCheckedForNotifications = true;

            contactIcon.setImage(new Image(getClass().getResource("/static/images/contacts.png").toExternalForm()));
            chatIcon.setImage(new Image(getClass().getResource("/static/images/chat2.png").toExternalForm()));

            notificationsIcon.setImage(new Image(getClass().getResource("/static/images/bellBlue.png").toExternalForm()));

            removeTextBackground(contactText);
            removeTextBackground(chatText);
            if (isCheckedForNotifications) {
                addNewContactPane.setVisible(false);
                newChatPane.setVisible(false);
                changeTheTextColor(notificationText);
            }

        });

    }

    void changeTheTextColor(Label label) {
        // image.setImage("followers.png");
        label.setTextFill(Color.web("#0078D4"));

    }

    void removeTextBackground(Label label) {
        label.setTextFill(Color.web("#000000"));

    }

}
