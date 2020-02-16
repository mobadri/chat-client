package com.chat.client.view.client.user;

import com.chat.server.model.user.User;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.FileChooser;

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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        image = new Image(getClass().getResource("/static/images/user-home/myProfilePicture.png").toString());
    }

    @FXML
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

    public void setUser(User user) {
        this.user = user;
        setData(user);
    }

    public void setData(User user) {
        System.out.println(user);
        nameOfUser.setText(user.getFirstName() + " " + user.getLastName());
        bioOfUser.setText(user.getBIO());
        phoneOfUser.setText(user.getPhone());
        nameOfProfile.setText(user.getFirstName() + " " + user.getLastName());
    }


}
