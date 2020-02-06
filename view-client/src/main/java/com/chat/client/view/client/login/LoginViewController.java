package com.chat.client.view.client.login;

import com.chat.client.controller.client.user.UserController;
import com.chat.server.model.user.User;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

public class LoginViewController {
    UserController userController = new UserController();
    @FXML
    AnchorPane signInView;

    @FXML
    AnchorPane signUpView;

    @FXML
    AnchorPane parentPane;

    @FXML
    JFXTextField phone;

    @FXML
    JFXPasswordField password;

    public void signUpView(ActionEvent actionEvent) {
        signUpView.toFront();
    }

    public void signInView(ActionEvent actionEvent) {
        signInView.toFront();
    }

    public void closeApp(MouseEvent mouseEvent) {
        System.exit(0);
    }

    @FXML
    private void onLogin(MouseEvent mouseEvent) {
        User user = userController.login(phone.getText(), password.getText());
        if (user != null) {
            System.out.println(user);
        } else {
            System.out.println("notfound");
        }
    }
}
