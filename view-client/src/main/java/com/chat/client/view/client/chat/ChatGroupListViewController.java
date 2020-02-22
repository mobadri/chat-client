package com.chat.client.view.client.chat;

import com.chat.client.view.client.chat.render.ChatGroupCellRenderer;
import com.chat.server.model.chat.ChatGroup;
import com.chat.server.model.user.User;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
import javafx.application.Platform;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class ChatGroupListViewController implements Initializable {
    //----------------------------------------------------------------
    //----------------------------view section -----------------------
    //----------------------------------------------------------------

    @FXML
    private JFXButton newGroupBtn;
    @FXML
    private JFXListView<ChatGroup> chatGroupListView;

    //----------------------------------------------------------------
    //----------------------------data section -----------------------
    //----------------------------------------------------------------
    // view list items and property
    private ObservableList<ChatGroup> chatGroupObservableList = FXCollections.observableArrayList();
    private ListProperty<ChatGroup> chatGroupProperty = new SimpleListProperty<>();

    //----------------------------------------------------------------
    //----------------------------references section -----------------------
    //----------------------------------------------------------------

    // parent home
    private UserHome userHome;

    // current login user
    private User currentUser;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    @FXML
    private void onChatGroupListViewClicked(MouseEvent mouseEvent) {
        setSelectedChatGroup();
    }

    /**
     * set list view data and
     * bind property list
     *
     * @param chatGroups all groups to be set on the view
     */
    private void setChatGroupListView(List<ChatGroup> chatGroups) {
        Platform.runLater(() -> {
            chatGroupObservableList = FXCollections.observableList(chatGroups);
            chatGroupProperty.set(chatGroupObservableList);
            chatGroupListView.itemsProperty().bindBidirectional(chatGroupProperty);
            chatGroupListView.setItems(chatGroupProperty);
            chatGroupListView.setCellFactory(new ChatGroupCellRenderer());
        });
    }

    /**
     * set current user data to get all user chat groups and set it on the list view
     *
     * @param currentUser login user
     */
    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
        // set chat group on the view list
        setChatGroupListView(currentUser.getChatGroups());
    }

    /**
     * set selected chat group from list view to parent view section
     */
    public void setSelectedChatGroup() {
        if (!chatGroupObservableList.isEmpty()) {
            // get selected
            // set the view on the parent home with selected chat group
            chatGroupListView.getSelectionModel().getSelectedItem();

        }

    }

}
