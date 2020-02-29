package com.chat.client.view.client.login;

import com.chat.client.controller.client.user.login.SignUpAndRegistration;
import com.chat.server.model.user.Gender;
import com.chat.server.model.user.User;
import com.jfoenix.controls.JFXComboBox;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.rmi.RemoteException;
import java.util.*;
import java.util.stream.Collectors;

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
    private JFXComboBox<String> comboBoxSignUpCountry;

    @FXML
    private Button btnSignUpNext;
    @FXML
    private Gender gender = Gender.MALE;
    @FXML
    RadioButton male;
    @FXML
    RadioButton female;


    @FXML
    private Label InvalidLastName;

    @FXML
    private Label InvalidFirstName;

    @FXML
    private Label InvalidPhone;

    @FXML
    private Label InvalidPassword;

    @FXML
    private Label InvalidEmail;

    @FXML
    private Label InvalidCountry;

    @FXML
    private ImageView back;
    boolean isMaximized = false;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        new Thread(() -> {
            Platform.runLater(() -> {
                loadCountries();
            });
        }).start();
    }

    private void loadCountries() {
        List<String> collect = Arrays.asList(Locale.getAvailableLocales())
                .parallelStream().map(Locale::getDisplayCountry)
                .filter(s -> !s.isEmpty())
                .sorted()
                .collect(Collectors.toList());
        comboBoxSignUpCountry.setItems(FXCollections.observableList(collect));
    }

    public void setStageSignUp(Stage myStage) {
        this.stage = myStage;
        stage.setOnCloseRequest((e) -> System.exit(0));
    }


    @FXML
    void onNextAction(ActionEvent event) {
        clearValidation();


        Map<String, Boolean> validationMap = new HashMap<>();
        if (txtFieldSignUpPassword.getText().equals(txtFieldSignUpConfirmPassword.getText())) {
            txtFieldSignUpPassword.setStyle("-fx-border-color: gray ; -fx-border-width: 1px ;");
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
                        setError(key, value);
                    });
                } else {
                    loadNextView(user);
                }
            } catch (RemoteException e) {
                e.printStackTrace();
            }

        } else {
            InvalidPassword.setText("* Invalid Password");
            txtFieldSignUpPassword.setStyle("-fx-border-color: red ; -fx-border-width: 1px ;");
        }
    }

    private void clearValidation() {
        InvalidFirstName.setText("");
        InvalidLastName.setText("");
        InvalidPhone.setText("");
        InvalidPassword.setText("");
        InvalidEmail.setText("");
        InvalidCountry.setText("");
        txtFieldSignUpFirstName.setStyle("-fx-border-color: gray; -fx-border-width: 1px ;");
        txtFieldSignUpLastName.setStyle("-fx-border-color: gray ; -fx-border-width: 1px ;");
        txtFieldSignUpPhoneNumber.setStyle("-fx-border-color: gray ; -fx-border-width: 1px ;");
        txtFieldSignUpPassword.setStyle("-fx-border-color: gray ; -fx-border-width: 1px ;");
        txtFieldSignUpEmail.setStyle("-fx-border-color: gray ; -fx-border-width: 1px ;");
        comboBoxSignUpCountry.setStyle("-fx-border-color: gray ; -fx-border-width: 1px ;");
    }

    private void setError(String key, Boolean value) {
        switch (key) {
            case "InvalidFirstName":
                InvalidFirstName.setText("* Invalid First Name");
                txtFieldSignUpFirstName.setStyle("-fx-border-color: red ; -fx-border-width: 1px ;");
                break;
            case "InvalidLastName":
                InvalidLastName.setText("* Invalid Last Name");
                txtFieldSignUpLastName.setStyle("-fx-border-color: red ; -fx-border-width: 1px ;");
                break;
            case "InvalidPhone":
                InvalidPhone.setText("*Invalid Phone");
                txtFieldSignUpPhoneNumber.setStyle("-fx-border-color: red ; -fx-border-width: 1px ;");
                break;
            case "InvalidPassword":
                InvalidPassword.setText("* Weak Password At least 8 letters");
                txtFieldSignUpPassword.setStyle("-fx-border-color: red ; -fx-border-width: 1px ;");
                break;
            case "InvalidEmail":
                InvalidEmail.setText("* Invalid Email");
                txtFieldSignUpEmail.setStyle("-fx-border-color: red ; -fx-border-width: 1px ;");

                break;
            case "InvalidCountry":
                InvalidCountry.setText("*Invalid Country");
                comboBoxSignUpCountry.setStyle("-fx-border-color: red ; -fx-border-width: 1px ;");
                break;

        }

    }

    private User mapUserFromFields() {
        User user = new User();
        user.setPassword(txtFieldSignUpPassword.getText().trim());
        user.setFirstName(txtFieldSignUpFirstName.getText().trim());
        user.setLastName(txtFieldSignUpLastName.getText().trim());
        user.setPhone(txtFieldSignUpPhoneNumber.getText().trim());
        user.setEmail(txtFieldSignUpEmail.getText().trim());
        user.setCountry(comboBoxSignUpCountry.getSelectionModel().getSelectedItem());
        user.setGender(gender);
        return user;
    }

    private void loadNextView(User user) {
        Parent root;
        try {

            FXMLLoader loader =
                    new FXMLLoader(getClass().getResource("/templates/login/secondpagesignup2.fxml"));
            root = loader.load();
            System.out.println(stage);
            SecondPageSignUpController secondpagesignupController = loader.getController();
            System.out.println(user);
            System.out.println(secondpagesignupController);
            secondpagesignupController.setUserFromFirstPage(user);
            secondpagesignupController.setStage(stage);

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

    @FXML
    public void exit(MouseEvent mouseEvent) {
        Stage stage = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
        stage.close();
    }

    @FXML
    public void minimize(MouseEvent mouseEvent) {
        Stage stage = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
        stage.setIconified(true);
    }

    @FXML
    public void maximize(MouseEvent mouseEvent) {

        Stage stage = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
        stage.setFullScreenExitHint(" ");

        if (!isMaximized) {
            isMaximized = true;
            stage.setMaximized(true);
        } else {
            isMaximized = false;
            stage.setMaximized(false);
        }
    }


}