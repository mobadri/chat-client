package com.chat.client.view.client.user;

import com.chat.client.controller.client.user.HomeControllerImpl;
import com.chat.client.controller.client.user.UserHomeInterface;
import com.chat.server.model.user.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class UserProfileController implements Initializable {

    @FXML
    private Image image;

    @FXML
    private Circle circleImage;

    @FXML
    private Label nameOfUser;

    @FXML
    private Label bioOfUser;

    @FXML
    private Label phoneOfUser;

    @FXML
    private Label nameOfProfile;
    private User user;

    private UserHomeInterface homeController;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        image = new Image(getClass().getResource("/static/images/user-home/myProfilePicture.png").toString());
    }


    private Image imageChooser(MouseEvent event) throws IOException {

        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif"));
        File file = fileChooser.showOpenDialog(((Node) event.getSource()).getScene().getWindow());
        System.out.println(file.toURI().toString());

        image = new Image(file.toURI().toString());
        System.out.println("image started to  loaded <<");
        return image;

    }

    @FXML
    private void chatImageHandelr(MouseEvent event) {

        try {
            image = imageChooser(event);
            circleImage.setFill(new ImagePattern(image));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setViewData(User user) {
        System.out.println(user);
        nameOfUser.setText(user.getFirstName() + " " + user.getLastName());
        bioOfUser.setText(user.getBIO());
        phoneOfUser.setText(user.getPhone());
        nameOfProfile.setText(user.getFirstName() + " " + user.getLastName());
    }

    public void setUser(User user) {
        this.user = user;
        setViewData(user);
    }



    @FXML
    public void editUserAction(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/templates/user/userdata-view-client.fxml"));
            Parent root = loader.load();
            UserDataViewController userDataViewController = loader.getController();
            Stage stage = new Stage();
            stage.setResizable(false);
            stage.setTitle("Edit Profile");
            stage.setScene(new Scene(root));
            userDataViewController.setStage(stage);
            userDataViewController.setUserController(homeController);
            userDataViewController.setUser(user);
            stage.showAndWait();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setHomeController(UserHomeInterface homeController) {
        this.homeController = homeController;
    }
}
