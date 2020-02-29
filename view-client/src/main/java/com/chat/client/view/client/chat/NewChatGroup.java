package com.chat.client.view.client.chat;

import com.chat.client.view.client.chat.render.CellRenderer;
import com.chat.server.model.chat.ChatGroup;
import com.chat.server.model.user.User;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTextField;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class NewChatGroup extends Dialog implements Initializable {
    @FXML
    public Label tilte;
    @FXML
    public Button addOrUpdate;
    @FXML
    private JFXListView<User> friendsList;
    @FXML
    private JFXListView<User> chatGroupList;
    @FXML
    private JFXTextField chatGroupName;
    @FXML
    private Label error;
    @FXML
    private Label errorlist;

    private Stage stage;
    //--------------------------------------------
    //---------------data section-----------------
    //--------------------------------------------

    private List<User> userFriendsList = new ArrayList<>();
    private List<User> userInGroupList = new ArrayList<>();
    private ListProperty<User> friendsListProperty = new SimpleListProperty<>();
    private ListProperty<User> chatGroupUserListProperty = new SimpleListProperty<>();
    private User currentUser;
    private ChatGroupListViewController chatGroupListViewController;
    private ChatViewController chatViewController;
    private ChatGroup chatGroup;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }


    @FXML
    private void addFriendToChatGroup(ActionEvent actionEvent) {
        User friend = friendsList.getSelectionModel().getSelectedItem();
        if (!friendsListProperty.isEmpty() && friend != null) {
            friendsListProperty.remove(friend);
            chatGroupUserListProperty.add(friend);
        }
    }

    @FXML
    private void backFriendToFriendList(ActionEvent actionEvent) {
        User friend = chatGroupList.getSelectionModel().getSelectedItem();
        if (!chatGroupUserListProperty.isEmpty() && friend != null) {
            chatGroupUserListProperty.remove(friend);
            friendsListProperty.add(friend);
        }
    }
//    pr
    //--------------------------------------------------------------------
    //---------------------------view section-----------------------------
    //--------------------------------------------------------------------

    private void setFriendsListView(List<User> users) {
        for (User user : users) {
            if (!checkIfInGroup(user)) {
                userFriendsList.add(user);
            }
        }
        friendsListProperty.setValue(FXCollections.observableList(userFriendsList));
        friendsList.itemsProperty().bindBidirectional(friendsListProperty);
        friendsList.setItems(friendsListProperty);
        friendsList.setCellFactory(new CellRenderer());
    }

    private boolean checkIfInGroup(User friend) {
        for (User user : chatGroup.getUsers()) {
            if (user.getId() == friend.getId()) {
                return true;
            }
        }
        return false;
    }

    private void setChatGroupListView(List<User> users) {
        for (User user : users) {
            if (user.getId() != currentUser.getId()) {
                userInGroupList.add(user);
            }
        }
        chatGroupUserListProperty.setValue(FXCollections.observableList(userInGroupList));
        chatGroupList.itemsProperty().bindBidirectional(chatGroupUserListProperty);
        chatGroupList.setItems(chatGroupUserListProperty);
        chatGroupList.setCellFactory(new CellRenderer());
    }

    //----------------------------------------------------------------------
    //-----------------------Data section---------------------------------
    //----------------------------------------------------------------------

    @FXML
    private void createChatGroup() {
        error.setText("");
        errorlist.setText("");
        if (chatGroup.getId() > 0) {
            updateChatGroup();
        } else {
            insertNewGroup();
        }


    }

    private void updateChatGroup() {
        // update chat group

    }

    private void insertNewGroup() {
        if (chatGroupName.getText() == null || chatGroupName.getText().trim().equals("")) {
            error.setText("please enter this field");
        } else {
            ChatGroup chatGroup = new ChatGroup();
            chatGroup.setName(chatGroupName.getText());

            if (!chatGroupUserListProperty.isEmpty()) {
                chatGroup = chatGroupListViewController.insetNewChatGroup(chatGroup);
                if (chatGroup != null && chatGroup.getId() > 0) {
                    chatGroup = chatGroupListViewController.appendUserToChatGroup(chatGroup, currentUser);
                    chatGroup.getUsers().add(currentUser);
                    for (User user : chatGroupUserListProperty) {
                        chatGroup = chatGroupListViewController.appendUserToChatGroup(chatGroup, user);
                        chatGroup.getUsers().add(user);
                    }
                    chatGroupListViewController.appendChatGroup(chatGroup);
                    stage.close();
                }
            } else {
                errorlist.setText("please add friends to list");
            }
        }
    }

    @FXML
    private void close(ActionEvent actionEvent) {
        stage.close();
    }

    //----------------------------------------------------------------------
    //-----------------------setter section---------------------------------
    //----------------------------------------------------------------------

    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }

    public void setChatGroupListViewController(ChatGroupListViewController chatGroupListViewController) {
        this.chatGroupListViewController = chatGroupListViewController;
    }

    public void setChatViewController(ChatViewController chatViewController) {
        this.chatViewController = chatViewController;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void setChatGroup(ChatGroup chatGroup) {
        this.chatGroup = chatGroup;
        setChatGroupListView(chatGroup.getUsers());
        setFriendsListView(currentUser.getFriends());
        if (chatGroup.getId() > 0) {
            // set data for old group
            // load all data on group
            tilte.setText("Update group");
            addOrUpdate.setText("Update chat group");
        } else {
            // new group
            // load all user on friend list
            tilte.setText("Add new chat group");
            addOrUpdate.setText("Add chat group");

        }
    }

}
