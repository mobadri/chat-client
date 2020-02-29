package com.chat.client.view.client.chat;

import com.chat.client.view.client.chat.render.ChatGroupCellRenderer;
import com.chat.client.view.client.user.UserViewHome;
import com.chat.server.model.chat.ChatGroup;
import com.chat.server.model.user.User;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
import javafx.application.Platform;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
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

    private Parent newChatGroupView;

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
    private UserViewHome userHome;

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
     * set user home view reference for current view
     *
     * @param userHome current parent
     */
    public void setUserHome(UserViewHome userHome) {
        this.userHome = userHome;
    }

    public JFXListView<ChatGroup> getChatGroupListView() {
        return chatGroupListView;
    }

    /**
     * set selected chat group from list view to parent view section
     */
    public void setSelectedChatGroup() {

        if (!chatGroupObservableList.isEmpty()) {
            ChatGroup chatGroup = chatGroupListView.getSelectionModel().getSelectedItem();
            if (chatGroup != null && chatGroupObservableList.size() > 0) {
                int selectedIndex = chatGroupListView.getSelectionModel().getSelectedIndex();
                userHome.setOnMainPane(userHome.getChatViewList().get(selectedIndex));
            }
        }
    }

    @FXML
    private void viewAddNewChatGroup(ActionEvent actionEvent) {
        NewChatGroup newChatGroup = loadNewChatGroup();
        Scene scene = new Scene(newChatGroupView);
        Stage stage = new Stage();
        newChatGroup.setStage(stage);
        stage.setScene(scene);
        stage.showAndWait();

    }

    private NewChatGroup loadNewChatGroup() {
        NewChatGroup newChatGroup = null;
        try {
            FXMLLoader loader =
                    new FXMLLoader(this.getClass().getResource("/templates/chat/new-chat-group.fxml"));
            newChatGroupView = loader.load();
            newChatGroup = loader.getController();
            newChatGroup.setCurrentUser(currentUser);
            newChatGroup.setChatGroup(new ChatGroup());
            newChatGroup.setChatGroupListViewController(this);
            newChatGroup.initModality(Modality.APPLICATION_MODAL);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return newChatGroup;
    }

    public void appendChatGroup(ChatGroup chatGroup) {
        chatGroupProperty.add(chatGroup);
    }

    public ChatGroup appendUserToChatGroup(ChatGroup chatGroup, User user) {
        return userHome.addFriendToChatGroup(chatGroup, user);
    }

    public ChatGroup insetNewChatGroup(ChatGroup chatGroup) {
        return userHome.appendChatGroup(chatGroup);
    }
}