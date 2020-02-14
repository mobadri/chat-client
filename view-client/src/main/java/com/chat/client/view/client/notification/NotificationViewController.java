package com.chat.client.view.client.notification;

import com.chat.client.controller.client.pushNotifications.PushNotificationInterface;
import com.chat.server.model.chat.Notification;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.shape.Polyline;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ResourceBundle;

public class NotificationViewController implements Initializable , PushNotificationInterface {
    @FXML
    private Rectangle notificationRoot;

    @FXML
    private Text notificationType;

    @FXML
    private Polyline notificationPolyline;

    @FXML
    private Text notificationMessage;

    @FXML
    private Label viewNotification;

    @FXML
    private Label dismissNotification;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    @Override
    public void receiveNotification(Notification notification) {
        System.out.println(notification);
    }
}
