package com.chat.client.view.client.login;

import com.chat.client.controller.client.user.login.SignUpAndRegistration;
import com.chat.server.model.user.Gender;
import com.chat.server.model.user.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

public class FirstSignUpController implements Initializable {
    private SignUpAndRegistration signUpAndRegistration;


    private Stage stage;
    @FXML
    private TextField txtFieldSignUpFirstName;

    @FXML
    private TextField txtFieldSignUpLastName;

    @FXML
    private TextField txtFieldSignUpPhoneNumber;

    @FXML
    private PasswordField txtFieldSignUpPassword;

    @FXML
    private PasswordField txtFieldSignUpConfirmPassword;

    @FXML
    private TextField txtFieldSignUpEmail;

    @FXML
    private TextField txtFieldSignUpCountry;

    @FXML
    private Button btnSignUpNext;
    @FXML
    private Gender gender = Gender.MALE;
    @FXML
    RadioButton male;
    @FXML
    RadioButton female;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }

    public void setStageSignUp(Stage myStage) {
        this.stage = myStage;
    }

    @FXML
    void onNextAction(ActionEvent event) {


        if (txtFieldSignUpPassword.getText().trim()
                .equals(txtFieldSignUpConfirmPassword.getText().trim())) {
            Map<String, Boolean> validationMap = new HashMap<>();
            User user = mapUserFromFields();
            try {
                Map<String, Boolean> validateMap = signUpAndRegistration.validate(user);
                validateMap.forEach((key, valid) -> {
                    if (!valid) {
                        validationMap.put(key, valid);
                    }
                });
                if (validationMap.size() > 0) {
                    validationMap.forEach((key, value) -> {
                        System.out.println(key + " " + value);
                    });
                } else {
                    loadNextView();
                }
            } catch (RemoteException e) {
                e.printStackTrace();
            }

        } else {

        }


    }

    private User mapUserFromFields() {
        User user = new User();
        user.setPassword(txtFieldSignUpPassword.getText());
        user.setFirstName(txtFieldSignUpFirstName.getText().trim());
        user.setLastName(txtFieldSignUpLastName.getText().trim());
        user.setPhone(txtFieldSignUpPhoneNumber.getText().trim());
        user.setEmail(txtFieldSignUpEmail.getText());
        user.setCountry(txtFieldSignUpCountry.getText());
        user.setGender(gender);
        return user;
    }

    private void loadNextView() {
        Parent root;
        try {

            FXMLLoader loader =
                    new FXMLLoader(getClass().getResource("/templates/login/secondpagesignup.fxml"));
            root = loader.load();
            System.out.println(stage);
            stage.setScene(new Scene(root));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setSignUpAndRegistration(SignUpAndRegistration signUpAndRegistration) {
        System.out.println("setSignUpAndRegistration");
        this.signUpAndRegistration = signUpAndRegistration;
    }

    @FXML
    private void setGender(ActionEvent actionEvent) {
        RadioButton radioButton = (RadioButton) actionEvent.getSource();
        if (radioButton.isSelected()) {
            if (radioButton.getText().equals("Male")) {
                gender = Gender.MALE;
            } else {
                gender = Gender.FEMALE;
            }
            System.out.println(gender);
        }
    }
}
