package com.chat.client.view.client.chat;

import com.chat.client.controller.client.chatGroup.ChatGroupController;
import com.chat.client.controller.client.pushNotifications.PushNotificationController;
import com.chat.client.controller.client.pushNotifications.PushNotificationInterface;
import com.chat.client.controller.client.user.HomeController;
import com.chat.client.view.client.user.UserProfileController;
import com.chat.server.model.chat.ChatGroup;
import com.chat.server.model.chat.Notification;
import com.chat.server.model.user.User;
import javafx.application.Platform;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.controlsfx.control.Notifications;

import java.io.IOException;
import java.net.URL;
import java.rmi.RemoteException;
import java.util.List;
import java.util.ResourceBundle;

public class UserHome implements Initializable, PushNotificationInterface {
    @FXML
    private TextField searchforfriends;
    @FXML
    private Button addFriend;
    private HomeController homeController;
    @FXML
    private ListView userList;
    @FXML
    private ListView chatGroupList;
    @FXML
    private AnchorPane containerPane;
    @FXML
    private AnchorPane friendsAnchorPane;
    @FXML
    private AnchorPane ChatGroupAnchorPane;

    private boolean showList = true;
    ListProperty<User> myFriendsListProperty = new SimpleListProperty<>();
    private ObservableList<User> myFriendsList = FXCollections.observableArrayList();

    private ObservableList<ChatGroup> myChatGroupsList = FXCollections.observableArrayList();


    Stage friendStage;

    //app controller
    private ChatGroupController chatGroupInterface;
    private PushNotificationController pushNotificationController;
    private User currentUser;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }


    public UserHome() {
        try {
            chatGroupInterface = new ChatGroupController();
            pushNotificationController = new PushNotificationController();
            pushNotificationController.setPushNotifications(this);
            pushNotificationController.setCurrentUser(currentUser);
            homeController = new HomeController();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public void nav(MouseEvent mouseEvent) {
    }

    public void logOut(MouseEvent mouseEvent) {
    }

    public void nav1(MouseEvent mouseEvent) {
    }

    private void setFriendsListView(List<User> users) {

        myFriendsList = FXCollections.observableList(users);
        userList.setItems(myFriendsList);
        userList.setCellFactory(new CellRenderer());

    }

    private void setChatGroupListView(List<ChatGroup> chatGroups) {

        myChatGroupsList = FXCollections.observableList(chatGroups);
//            new Thread(() -> {
//                List<ChatGroup> groups = new ArrayList<>();
//                for (ChatGroup chatGroup : chatGroups) {
//                    groups.add(homeController.getById(chatGroup.getId()));
//                }
////                Platform.runLater(() -> {
//                myChatGroupsList = FXCollections.observableList(groups);
//                chatGroupList.setItems(myChatGroupsList);
////                });
//            }).start();
        chatGroupList.setItems(myChatGroupsList);
        chatGroupList.setCellFactory(new ChatGroupCellRenderer());
    }

    @FXML
    private void onFriendsListClicked(MouseEvent mouseEvent) {
        User user = (User) userList.getSelectionModel().getSelectedItem();
        if (user != null) {
            loadFriendProfile(user);
        }
    }

    @FXML
    public void onchatGroupListClicked(MouseEvent mouseEvent) {
        ChatGroup chatGroup = (ChatGroup) chatGroupList.getSelectionModel().getSelectedItem();
        if (chatGroup != null) {
            loadChatGroup(chatGroup);
        }
    }

    private void loadFriendProfile(User user) {
        try {
            FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/templates/user/User_profile.fxml"));
            Parent root = loader.load();
            UserProfileController userProfileController = loader.getController();
            userProfileController.setUser(user);
            containerPane.getChildren().setAll(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private void loadChatGroup(ChatGroup chatGroup) {

        try {
            FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/templates/chat/chat-view.fxml"));
            Parent root = loader.load();
            //view controller
            ChatViewController chatViewController = (ChatViewController) loader.getController();
            chatViewController.setUser(currentUser);
            chatViewController.setChatGroup(chatGroup);
            //@yasmine
            //todo don't forget to add groupchat to chatviewcontroller
            //---------
            //add ref to each other
            chatGroupInterface.setChatGroupInterface(chatViewController);
            chatViewController.setChatGroupInterface(chatGroupInterface);

            containerPane.getChildren().setAll(root);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;

        new Thread(() -> {
            setFriendsListView(currentUser.getFriends());
        }).start();
        new Thread(() -> {
            setChatGroupListView(currentUser.getChatGroups());
        }).start();


        setSearchforfriends();
    }

    @Override
    public void receiveNotification(Notification notification) {
        Platform.runLater(() -> {
            Notifications.create()
                    .text(notification.getNotificationMessage())
                    .title(notification.getNotificationType().toString())
                    .showInformation();
        });
        System.out.println(notification);
    }

    public void onProfileclicked(MouseEvent mouseEvent) {

    }

    private void addFriend(User friend) {
        homeController.addFriend(currentUser, friend);
    }

    //------------------------------------------
    //@yassmin
    //------------------------------------------
    @FXML
    public void addFriend(MouseEvent mouseEvent) {
        Parent root;
        try {
            FXMLLoader loader =
                    new FXMLLoader(getClass().getResource("/templates/friend/addFriend.fxml"));
            root = loader.load();
            friendStage = new Stage();
            friendStage.setScene(new Scene(root));
            friendStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void searchTextListner(FilteredList<User> filteredData) {
        searchforfriends.textProperty().addListener((observable, oldValue, newValue) ->
                filteredData.setPredicate(friend -> {
                    if (newValue == null || newValue.isEmpty()) {
                        return true;
                    }
                    String lowerCaseFilter = newValue.toLowerCase();
                    if (friend.getFirstName().toLowerCase().contains(lowerCaseFilter)
                            || friend.getPhone().contains(lowerCaseFilter)
                            || (friend.getCountry() != null
                            && friend.getCountry().toLowerCase().contains(lowerCaseFilter))
                            || (friend.getLastName() != null
                            && friend.getLastName().toLowerCase().contains(lowerCaseFilter))) {
                        System.out.println("Search is here");
                        return true;
                    }
                    return false;
                }));
    }

    void setSearchforfriends() {
        FilteredList<User> filteredData = new FilteredList<>(myFriendsList, p -> true);
        searchTextListner(filteredData);
        SortedList<User> sortedData = new SortedList<>(filteredData);
        userList.setItems(sortedData);
    }

    @FXML
    private void navFriendList(ActionEvent actionEvent) {
        friendsAnchorPane.setVisible(showList);
        ChatGroupAnchorPane.setVisible(!showList);
        showList = !showList;
    }
}
