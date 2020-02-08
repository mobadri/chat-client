package com.chat.client.view.client.login;

import com.chat.client.controller.client.user.SignUpAndRegistration;
import com.chat.server.model.user.User;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

public class LoginViewController {
    SignUpAndRegistration signUpAndRegistration;
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

    public LoginViewController(SignUpAndRegistration signUpAndRegistration) {
        this.signUpAndRegistration = signUpAndRegistration;
    }

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
        User user = signUpAndRegistration.login(phone.getText(), password.getText());
        if (user != null) {
            System.out.println(user);
        } else {
            System.out.println("notfound");
        }
    }
}
