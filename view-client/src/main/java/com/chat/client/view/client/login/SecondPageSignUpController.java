package com.chat.client.view.client.login;

import com.chat.client.controller.client.user.login.RegistrationController;
import com.chat.client.controller.client.user.login.SignUpAndRegistration;
import com.chat.client.view.client.user.UserViewHome;
import com.chat.server.model.user.Mode;
import com.chat.server.model.user.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ResourceBundle;


public class SecondPageSignUpController implements Initializable {
    SignUpAndRegistration signUpAndRegistration;
    @FXML
    private TextField bio;
    @FXML
    private Circle userImage;
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
        stage.setOnCloseRequest((e) -> System.exit(0));
    }

    private void appendUserInfoFromSecondPage() {

        if (bio.getText() != null) user.setBIO(bio.getText());
        else user.setBIO("");
        LocalDate localDate = dateOfBirth.getValue();
        if (localDate != null) {
            user.setDateOfBirth(localDate);
        } else {

            user.setDateOfBirth(LocalDate.now());
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
        User savedUser = signUpAndRegistration.signUp(this.user, user.getPassword());
        if (savedUser.getId() > 0) {
            loadNextPage(savedUser);
        }
    }

    private void loadNextPage(User user) {
        Parent root;
        try {
            FXMLLoader loader =
                    new FXMLLoader(getClass().getResource("/templates/user/user-home-copy.fxml"));
            root = loader.load();
            UserViewHome userHomeController = loader.getController();
            userHomeController.setCurrentUser(user);
            stage.setScene(new Scene(root));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private byte[] readImage(String path) {
        byte[] image = null;
        try {
            image = Files.readAllBytes(Paths.get(path));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return image;
    }

    @FXML
    public void chooseImage(MouseEvent mouseEvent) {
        File file = openChooserDialog(mouseEvent);
        if (file != null) {
            Image image = new Image(file.toURI().toString());
            userImage.setFill(new ImagePattern(image));
            user.setImage(readImage(file.getPath()));
        }
    }

    private File openChooserDialog(MouseEvent mouseEvent) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif"));
        File file = fileChooser.showOpenDialog(((Node) mouseEvent.getSource()).getScene().getWindow());
        if (file != null) {
            return file;
        }
        return null;
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
    public void back(MouseEvent mouseEvent) {
        try {
            FXMLLoader loader =
                    new FXMLLoader(getClass().getResource("/templates/login/testsignup.fxml"));
            Parent root = loader.load();
            stage.setScene(new Scene(root));
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}