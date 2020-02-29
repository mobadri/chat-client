package com.chat.client.view.client.user;

import com.chat.client.controller.client.user.HomeControllerImpl;
import com.chat.client.controller.client.user.UserHomeInterface;
import com.chat.client.controller.client.user.login.RegistrationController;
import com.chat.client.controller.client.user.login.SignUpAndRegistration;
import com.chat.server.model.user.Gender;
import com.chat.server.model.user.Mode;
import com.chat.server.model.user.User;
import com.jfoenix.controls.*;
import com.jfoenix.validation.RequiredFieldValidator;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.net.URL;
import java.rmi.RemoteException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

public class UserDataViewController implements Initializable {


    @FXML
    public JFXTextField firstName;
    @FXML
    public JFXTextField lastName;
    @FXML
    public JFXPasswordField password;
    @FXML
    public JFXPasswordField confirmPassword;
    @FXML
    public JFXTextField email;
    @FXML
    public JFXTextField phone;
    @FXML
    public JFXComboBox country;
    @FXML
    public JFXDatePicker dateOfBirthh;
    @FXML
    public JFXTextField bio;
    @FXML
    public JFXRadioButton female;
    @FXML
    public JFXRadioButton male;
    @FXML
    public ToggleGroup genderToggel;

    @FXML
    public Label InvalidFirstName;
    @FXML
    public Label InvalidLastName;
    @FXML
    public Label InvalidPassword;
    @FXML
    public Label InvalidConfirmPassword;
    @FXML
    public Label InvalidEmail;
    @FXML
    public Label InvalidPhone;
    @FXML
    public Label InvalidCountry;
    @FXML
    public Label InvalidDateOfBirth;
    @FXML
    public Label title;
    public AnchorPane updateProfilePane;
    public JFXButton EditProfile;

    private User user;
    private Gender gender = Gender.MALE;
    private Stage stage;
    private UserHomeInterface homeController;
    private RequiredFieldValidator requiredFieldValidator = new RequiredFieldValidator();



    SignUpAndRegistration signUpAndRegistration = new RegistrationController();



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadAllCountries();
        checkAllField();
    }

    public static final LocalDate LOCAL_DATE(String dateString) {
        // DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDate localDate = LocalDate.parse(dateString, formatter);
        return localDate;
    }

    private void loadAllCountries() {
        List<String> collect = Arrays.asList(Locale.getAvailableLocales())
                .parallelStream().map(Locale::getDisplayCountry)
                .filter(s -> !s.isEmpty())
                .sorted()
                .collect(Collectors.toList());
        country.setItems(FXCollections.observableList(collect));

    }

    private void checkAllField() {
        requiredFieldValidator.setMessage("*No Input Field");
        firstName.getValidators().add(requiredFieldValidator);
        firstName.focusedProperty().addListener((observableValue, aBoolean, t1) -> {
            if (!t1) {
                firstName.validate();
            }
        });

        lastName.getValidators().add(requiredFieldValidator);
        lastName.focusedProperty().addListener((observableValue, aBoolean, t1) -> {
            if (!t1) {
                lastName.validate();
            }
        });

        password.getValidators().add(requiredFieldValidator);
        password.focusedProperty().addListener((observableValue, aBoolean, t1) -> {
            if (!t1) {
                password.validate();
            }
        });

        confirmPassword.getValidators().add(requiredFieldValidator);
        confirmPassword.focusedProperty().addListener((observableValue, aBoolean, t1) -> {
            if (!t1) {
                confirmPassword.validate();
            }
        });

        email.getValidators().add(requiredFieldValidator);
        email.focusedProperty().addListener((observableValue, aBoolean, t1) -> {
            if (!t1) {
                email.validate();
            }
        });

        phone.getValidators().add(requiredFieldValidator);
        phone.focusedProperty().addListener((observableValue, aBoolean, t1) -> {
            if (!t1) {
                phone.validate();
            }
        });

        country.getValidators().add(requiredFieldValidator);
        country.focusedProperty().addListener((observableValue, aBoolean, t1) -> {
            if (!t1) {
                country.validate();
            }
        });

        dateOfBirthh.getValidators().add(requiredFieldValidator);
        dateOfBirthh.focusedProperty().addListener((observableValue, aBoolean, t1) -> {
            if (!t1) {
                dateOfBirthh.validate();
            }
        });

        bio.getValidators().add(requiredFieldValidator);
        bio.focusedProperty().addListener((observableValue, aBoolean, t1) -> {
            if (!t1) {
                bio.validate();
            }
        });

    }


    private User setUserData() {
        if (user.getId() > 0) {
            user.setId(user.getId());
        }
        user.setFirstName(firstName.getText());
        user.setLastName(lastName.getText());
        user.setPassword(password.getText());
        user.setEmail(email.getText());
        System.out.println(phone.getText());
        user.setPhone(phone.getText());
        System.out.println(country.getValue() + " country");
        user.setCountry(country.getValue() == null ? "Egypt" : country.getValue().toString());
        user.setDateOfBirth(dateOfBirthh.getValue());
        user.setBIO(bio.getText());
        if (male.isSelected()) {
            user.setGender(Gender.MALE);
        } else if (female.isSelected()) ;
        user.setGender(Gender.FEMALE);
        user.setOnline(false);
        user.setMode(Mode.AVAILABLE);
        return user;
    }

    private void setViewData(User user) {
        if (user.getId() > 0) {
            title.setText("Update Profile");
        }
        firstName.setText(user.getFirstName());
        lastName.setText(user.getLastName());
        password.setText(user.getPassword());
        email.setText(user.getEmail());
        phone.setText(user.getPhone());
        if (user.getId() > 0) {
            phone.setEditable(false);
        }
        country.setValue(user.getCountry());
        dateOfBirthh.setValue(user.getDateOfBirth());
        bio.setText(user.getBIO());
    }

    private void clearValidation() {
        InvalidFirstName.setText("");
        InvalidLastName.setText("");
        InvalidPhone.setText("");
        InvalidPassword.setText("");
        InvalidEmail.setText("");
        InvalidCountry.setText("");
        firstName.setStyle("-fx-border-color: white; -fx-border-width: 1px ;");
        lastName.setStyle("-fx-border-color: white ; -fx-border-width: 1px ;");
        phone.setStyle("-fx-border-color: white ; -fx-border-width: 1px ;");
        password.setStyle("-fx-border-color: white ; -fx-border-width: 1px ;");
        confirmPassword.setStyle("-fx-border-color: white ; -fx-border-width: 1px ;");
        email.setStyle("-fx-border-color: white ; -fx-border-width: 1px ;");
        country.setStyle("-fx-border-color: white ; -fx-border-width: 1px ;");
    }

    private void setError(String key, Boolean value) {
        switch (key) {
            case "InvalidFirstName":
                InvalidFirstName.setText("* Invalid First Name");
                break;

            case "InvalidLastName":
                InvalidLastName.setText("* Invalid Last Name");
                break;

            case "InvalidPhone":
                InvalidPhone.setText("*Invalid Phone");
                break;

            case "InvalidPassword":
                InvalidPassword.setText("* Weak Pass At least 8 character");
                break;

            case "InvalidEmail":
                InvalidEmail.setText("* Invalid Email");
                break;

            case "InvalidCountry":
                InvalidCountry.setText("*Invalid Country");
                break;

            case "InvalidDateOfBirth":
                InvalidDateOfBirth.setText("*Invalid DateOfBirth");
                break;

        }
    }


    private boolean validateUser(User setUserData) {
        clearValidation();
        if (password.getText() != null && confirmPassword.getText() != null && password.getText().equals(confirmPassword.getText())) {
            Map<String, Boolean> validationMap = new HashMap<>();
            if (user != null) {
                Map<String, Boolean> validateMap = null;
                try {
                    validateMap = signUpAndRegistration.validate(setUserData);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
                validateMap.forEach((key, valid) -> {
                    if (!valid) {
                        validationMap.put(key, valid);
                    }
                });
                if (validationMap.size() > 0) {
                    validationMap.forEach((key, value) -> {
                        setError(key, value);
                        System.out.println(key + "" + value);
                    });
                } else {
                    return true;
                }
            }
        } else {

            InvalidPassword.setText("* Invalid Password");
//            password.setStyle("-fx-border-color: red ; -fx-border-width: 1px ;");
        }
        return false;
    }


    private void updateUser() {
        user = setUserData();
        User update = homeController.updateUser(user);
        if (update != null) {
//            showMessageDialog(Alert.AlertType.INFORMATION, "user has been updated successfully");
//            returnToParent();
            System.out.println("bjhbhjhjbhjb");
        } else {
            showMessageDialog(Alert.AlertType.ERROR, "error on update user call us !");
        }

    }



    @FXML
    public void editProfileAction(ActionEvent actionEvent) {
        boolean isValid = validateUser(setUserData());
        if (user != null && user.getId() > 0) {
            if (isValid) {
                updateUser();
//                User update = homeController.updateUser(setUserData());
//                System.out.println("new user "+update);
            }
        }
        Stage window = (Stage) EditProfile.getScene().getWindow();
        window.close();
    }



    public void setUser(User user) {
        this.user = user;
        setViewData(user);
    }


    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void setUserController(UserHomeInterface homeController) {

        this.homeController = homeController;
    }

    private void showMessageDialog(Alert.AlertType alertType, String message) {
        Alert alert = new Alert(alertType, message, ButtonType.OK);
        alert.showAndWait();
    }

    private void returnToParent() {
        stage.close();
    }
}

