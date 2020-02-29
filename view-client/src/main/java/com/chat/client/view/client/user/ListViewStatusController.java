package com.chat.client.view.client.user;


import com.chat.client.controller.client.user.HomeControllerImpl;
import com.chat.server.model.chat.Notification;
import com.chat.server.model.user.Mode;
import com.chat.server.model.user.User;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.svg.SVGGlyph;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.util.Callback;

import java.net.URL;
import java.util.ResourceBundle;


public class ListViewStatusController implements Initializable {

    @FXML
    private ListView<Mode> statusListView;
    private ObservableList<Mode> myModeList = FXCollections.observableArrayList(Mode.AVAILABLE, Mode.AWAY, Mode.BUSY);
    private ListProperty<Mode> myModeListProperty = new SimpleListProperty<>();
    private User currentUser;
    private Circle modeColor;

    public ListViewStatusController() {


    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        myModeListProperty.set(myModeList);
        System.out.println("myModeListProperty : " + myModeListProperty);
        System.out.println("statusListView : " + statusListView);
        statusListView.itemsProperty().bindBidirectional(myModeListProperty);
        statusListView.setItems(myModeListProperty);
        statusListView.setCellFactory(new Callback<ListView<Mode>, ListCell<Mode>>() {
            @Override
            public ListCell<Mode> call(ListView<Mode> modeListView) {
                return new UserStatus();
            }
        });
    }

    public void handleListView(MouseEvent mouseEvent) {
        statusListView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        Mode mode = statusListView.getSelectionModel().getSelectedItem();
        System.out.println(mode + "*************************");
        if (mode != null) {
            System.out.println(mode);
            HomeControllerImpl homeController = new HomeControllerImpl();
            switch (mode) {
                case AVAILABLE:
                    modeColor.setFill(Paint.valueOf("Green"));
                    homeController.changeMode(currentUser, Mode.AVAILABLE);
                    break;
                case AWAY:
                    modeColor.setFill(Paint.valueOf("yellow"));
                    System.out.println("oooooooooooo");
                    homeController.changeMode(currentUser, Mode.AWAY);
                    break;
                case BUSY:
                    modeColor.setFill(Paint.valueOf("RED"));
                    homeController.changeMode(currentUser, Mode.BUSY);
                    break;

            }
        }
    }

    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }

    public void setModeColor(Circle modeColor) {
        this.modeColor = modeColor;
    }
}