package com.chat.client.view.client.chat;

import com.chat.server.model.user.User;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.util.Callback;

public class ChatRendererwithbuttons implements Callback<ListView<User>, ListCell<User>> {
    @Override
    public ListCell<User> call(ListView<User> p) {

        ListCell<User> cell = new ListCell<User>() {

            @Override
            protected void updateItem(User user, boolean bln) {
                super.updateItem(user, bln);
                setGraphic(null);
                setText(null);
                if (user != null) {
                    HBox hBox = new HBox();

                    Text name = new Text(user.getFirstName() + " " + user.getLastName());

                    ImageView statusImageView = new ImageView();
                    System.out.println(user.getMode().toString().toLowerCase());
                    String path = "/static/images/mode/" + user.getMode().toString().toLowerCase() + ".png";
                    System.out.println(path);
//                    Image statusImage = new Image(getClass()
//                            .getResource("/static/images/mode/available.png").toString(), 16, 16, true, true);
  //                          .getResource("/static/images/mode/" + user.getMode().toString().toLowerCase().trim() + ".png").toString(), 16, 16, true, true);
    //                statusImageView.setImage(statusImage);

                    ImageView pictureImageView = new ImageView();
//                    Image image = new Image(getClass().getResource("/images/" + user.getPicture().toLowerCase() + ".png").toString(), 50, 50, true, true);
                    Image image = new Image(getClass().getResource("/static/images/Smile.png").toString(), 50, 50, true, true);
                    pictureImageView.setImage(image);
                    hBox.setSpacing(5);
                    hBox.getChildren().addAll(statusImageView, pictureImageView, name);
                    hBox.setAlignment(Pos.CENTER_LEFT);
                    VBox vbox = new VBox();
                    Button buttonaddFriend = new Button("ADD Friend");
                    Button buttonviewProfile = new Button("View Profile");
                    //setStyleForButtons
                    buttonaddFriend.setStyle(  "-fx-background-color: #0078D4; -fx-background-radius: 20; -fx-text-fill: #ffffff; -fx-font-size: 12px; -fx-alignment: CENTER;");
                    buttonviewProfile.setStyle(  "-fx-background-color: #0078D4; -fx-background-radius: 20; -fx-text-fill: #ffffff; -fx-font-size: 12px; -fx-alignment: CENTER;");

                    HBox hboxButtons = new HBox();
                    hboxButtons.setSpacing(5);


                    hboxButtons.setAlignment(Pos.CENTER_RIGHT);
                    hboxButtons.getChildren().addAll(buttonaddFriend,buttonviewProfile);
                    vbox.getChildren().addAll(hBox,hboxButtons);
                    setGraphic(vbox);
                }
            }
        };
        return cell;
    }
}


