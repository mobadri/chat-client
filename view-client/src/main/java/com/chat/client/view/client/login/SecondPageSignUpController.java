package com.chat.client.view.client.login;

import com.chat.client.controller.client.user.login.RegistrationController;
import com.chat.client.controller.client.user.login.SignUpAndRegistration;
import com.chat.client.view.client.chat.UserHome;
import com.chat.server.model.user.Mode;
import com.chat.server.model.user.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.ResourceBundle;


public class SecondPageSignUpController implements Initializable {
    SignUpAndRegistration signUpAndRegistration;
    @FXML
    private TextField bio;

    @FXML
    private DatePicker dateOfBirth;
    private Stage stage;
    private User user;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void setUserFromFirstPage(User user) {
        this.user = user;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }


    private void appendUserInfoFromSecondPage() {

        if (bio.getText() != null) user.setBIO(bio.getText());
        else user.setBIO("");
        LocalDate localDate = dateOfBirth.getValue();
        if (localDate != null) {
            Date date = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
            System.out.println(localDate);
            System.out.println(date);
            user.setDateOfBirth(date);
        } else {

            user.setDateOfBirth(new Date(1996, 7, 30));
        }
        System.out.println(user.getDateOfBirth());
        user.setMode(Mode.AVAILABLE);
        System.out.println(user);


    }

    @FXML
    public void handleButtonSignUp(ActionEvent actionEvent) {
        appendUserInfoFromSecondPage();

        SignUpAndRegistration signUpAndRegistration = new RegistrationController();
        System.out.println("fxml : " + user);
        User savedUser = signUpAndRegistration.signUp(this.user);
        if (savedUser.getId() > 0) {
            loadNextPage(savedUser);
        }
    }

    private void loadNextPage(User user) {
        Parent root;
        try {
            FXMLLoader loader =
                    new FXMLLoader(getClass().getResource("/templates/user/user-home.fxml"));
            root = loader.load();
            UserHome userHomeController = loader.getController();
            userHomeController.setCurrentUser(user);
            stage.setScene(new Scene(root));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}