package com.chat.client.view.client.chat;

import com.chat.client.controller.client.chatGroup.chatGroupDataContorller;
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
import javafx.scene.control.Dialog;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class NewChatGroup extends Dialog implements Initializable {
    @FXML
    public JFXListView<User> friendsList;
    @FXML
    public JFXListView<User> chatGroupList;
    @FXML
    public JFXTextField chatGroupName;

    //--------------------------------------------
    //---------------data section-----------------
    //--------------------------------------------

    private List<User> userFriendsList = new ArrayList<>();
    private ListProperty<User> friendsListProperty = new SimpleListProperty<>();
    private ListProperty<User> chatGroupUserListProperty = new SimpleListProperty<>();
    private chatGroupDataContorller chatGroupDataContoller = new chatGroupDataContorller();
    private User currentUser;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setChatGroupListView();
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
            userFriendsList.add(user);
        }
        friendsListProperty.setValue(FXCollections.observableList(userFriendsList));
        friendsList.itemsProperty().bindBidirectional(friendsListProperty);
        friendsList.setItems(friendsListProperty);
        friendsList.setCellFactory(new CellRenderer());
    }

    private void setChatGroupListView() {
        chatGroupUserListProperty.setValue(FXCollections.observableList(new ArrayList<>()));
        chatGroupList.itemsProperty().bindBidirectional(chatGroupUserListProperty);
        chatGroupList.setItems(chatGroupUserListProperty);
        chatGroupList.setCellFactory(new CellRenderer());
    }

    //----------------------------------------------------------------------
    //-----------------------Data section---------------------------------
    //----------------------------------------------------------------------

    @FXML
    private ChatGroup createChatGroup() {
        ChatGroup chatGroup = new ChatGroup();
        chatGroup.setName(chatGroupName.getText());
        if (!chatGroupUserListProperty.isEmpty()) {
            chatGroup = chatGroupDataContoller.createNewChatGroup(chatGroup);
            chatGroup = chatGroupDataContoller.addUserToChatGroup(chatGroup, currentUser);
            for (User user : chatGroupUserListProperty) {
                chatGroup = chatGroupDataContoller.addUserToChatGroup(chatGroup, user);
            }
        } else {
            System.out.println("can't insert");
        }
        return chatGroup;
    }

    //----------------------------------------------------------------------
    //-----------------------setter section---------------------------------
    //----------------------------------------------------------------------

    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
        setFriendsListView(currentUser.getFriends());
    }

}
