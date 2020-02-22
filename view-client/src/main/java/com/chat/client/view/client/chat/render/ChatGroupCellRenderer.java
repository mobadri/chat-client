package com.chat.client.view.client.chat.render;

import com.chat.server.model.chat.ChatGroup;
import com.chat.server.model.user.User;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.util.Callback;

/**
 * A Class for Rendering users images / name on the userlist.
 */
public class ChatGroupCellRenderer implements Callback<ListView<ChatGroup>, ListCell<ChatGroup>> {
    @Override
    public ListCell<ChatGroup> call(ListView<ChatGroup> p) {

        ListCell<ChatGroup> cell = new ListCell<ChatGroup>() {
            @Override
            protected void updateItem(ChatGroup chatGroup, boolean bln) {
                super.updateItem(chatGroup, bln);
                setGraphic(null);
                setText(null);
                if (chatGroup != null) {
                    VBox vBox = new VBox();
                    HBox hBox = new HBox();
                    HBox hBoxOnline = new HBox();

                    Text name = new Text(chatGroup.getName());
                    long onlineUsers = chatGroup.getUsers().parallelStream().filter(User::isOnline).count();
                    Label onlineLabel = new Label("online users = " + onlineUsers);
                    ImageView pictureImageView = new ImageView();
                    Image image = new Image(getClass().getResource("/static/images/Smile.png").toString(), 50, 50, true, true);
                    pictureImageView.setImage(image);

                    hBox.getChildren().addAll(pictureImageView, name);
                    hBox.setAlignment(Pos.CENTER_LEFT);
                    hBox.setSpacing(5);

                    hBoxOnline.getChildren().addAll(onlineLabel);
                    hBoxOnline.setAlignment(Pos.CENTER_LEFT);
                    hBoxOnline.setSpacing(5);
                    vBox.getChildren().addAll(hBox, hBoxOnline);
                    vBox.setAlignment(Pos.CENTER_LEFT);
                    vBox.setFillWidth(true);
                    setPrefHeight(80);
                    setGraphic(vBox);
                }
            }
        };
        return cell;
    }

}