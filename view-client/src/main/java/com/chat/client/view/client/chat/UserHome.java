package com.chat.client.view.client.chat;

import com.chat.client.controller.client.chatGroup.ChatGroupController;
import com.chat.client.controller.client.pushNotifications.PushNotificationController;
import com.chat.client.controller.client.pushNotifications.PushNotificationInterface;
import com.chat.client.controller.client.user.HomeController;
import com.chat.client.view.client.notification.traynotifications.animations.AnimationType;
import com.chat.client.view.client.notification.traynotifications.notification.TrayNotification;
import com.chat.client.view.client.user.UserProfileController;
import com.chat.server.model.chat.ChatGroup;
import com.chat.server.model.chat.Notification;
import com.chat.server.model.chat.NotificationType;
import com.chat.server.model.user.Mode;
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
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.rmi.RemoteException;
import java.util.ArrayList;
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

    private ObservableList<Notification> myNotificationList = FXCollections.observableArrayList();
    private List<ChatGroupController> chatGroupControllerList = new ArrayList<>();
    private List<Parent> chatViewList = new ArrayList<>();


    Stage friendStage;


    private PushNotificationController pushNotificationController;
    private User currentUser;

    public UserHome() {
        try {

            pushNotificationController = new PushNotificationController();
            pushNotificationController.setPushNotifications(this);

            homeController = new HomeController();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

//    void setSearchforfriends() {
//        FilteredList<User> filteredData = new FilteredList<>(myFriendsList, p -> true);
//        searchTextListner(filteredData);
//        SortedList<User> sortedData = new SortedList<>(filteredData);
//        userList.setItems(sortedData);
//    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        //setListView();
//        setSearchforfriends();
    }

    public void nav(MouseEvent mouseEvent) {
    }

    public void logOut(MouseEvent mouseEvent) {
    }

    public void nav1(MouseEvent mouseEvent) {
    }

    private void setFriendsListView(List<User> users) {
        System.out.println(users.size());
        myFriendsList = FXCollections.observableList(users);
        System.out.println(users.size());
        userList.setItems(myFriendsList);
        userList.setCellFactory(new CellRenderer());

    }

    private void setChatGroupListView(List<ChatGroup> chatGroups) {
        myChatGroupsList = FXCollections.observableList(chatGroups);
        new Thread(() -> {
            List<ChatGroup> groups = new ArrayList<>();
            for (ChatGroup chatGroup : chatGroups) {
                groups.add(homeController.getById(chatGroup.getId()));
            }

            Platform.runLater(() -> {
                myChatGroupsList = FXCollections.observableList(groups);
                chatGroupList.setItems(myChatGroupsList);
                loadAllChatGroupViewOnMemory(myChatGroupsList);
            });
        }).start();
        chatGroupList.setItems(myChatGroupsList);
        chatGroupList.setCellFactory(new ChatGroupCellRenderer());
    }

    private void loadAllChatGroupViewOnMemory(ObservableList<ChatGroup> myChatGroupsList) {
        for (ChatGroup chatGroup : myChatGroupsList) {
            System.out.println(chatGroup.getId());
            loadChatGroup(chatGroup);
        }
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
        if (chatGroup != null && chatViewList.size() > 0) {
            int selectedIndex = chatGroupList.getSelectionModel().getSelectedIndex();
            System.out.println("selectedIndex =" + selectedIndex);
            System.out.println("chatViewList.size =" + chatViewList.size()
            );
            containerPane.getChildren().setAll(chatViewList.get(chatGroupList.getSelectionModel().getSelectedIndex()));
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
            //---------
            //add ref to each other
            //app controller
            ChatGroupController chatGroupInterface = new ChatGroupController();
            chatGroupInterface.setChatGroupInterface(chatViewController);
            chatViewController.setChatGroupInterface(chatGroupInterface);

            chatGroupInterface.setChatGroup(chatGroup);
            chatGroupInterface.setCurrentUser(currentUser);

            chatGroupControllerList.add(chatGroupInterface);
            chatViewList.add(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
        pushNotificationController.setCurrentUser(currentUser);
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


        Platform.runLater(() ->

        {

            Image profileImg = new Image(getClass().getResource("/static/images/AddPic.png").toString(), 50, 50, false, false);
            TrayNotification tray = new TrayNotification();
            tray.setTitle(notification.getNotificationType().toString());
            tray.setMessage(notification.getNotificationMessage());
            tray.setRectangleFill(Paint.valueOf("#2C3E50"));
            tray.setAnimationType(AnimationType.SLIDE);
            tray.setImage(profileImg);
            tray.showAndDismiss(Duration.seconds(5));
            tray.setNotificationType(NotificationType.MESSAGE_RECEIVED);
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
    /*void setSearchforfriends() {
        FilteredList<User> filteredData = new FilteredList<>(myFriendsList, p -> true);
        searchTextListner(filteredData);
        SortedList<User> sortedData = new SortedList<>(filteredData);
        userList.setItems(sortedData);
    }*/

    private void changeFriendsStatus(User user) {
        for (User user1 : myFriendsList) {
            if (user1.getId() == user.getId()) {
                user1.setMode(user.getMode());
            }
        }
    }

    private void removeOfflineFriends(User user) {
        if (user.getMode() == Mode.AWAY) {
            for (User user1 : myFriendsList) {
                if (user1.getId() == user.getId()) {
                    myFriendsList.remove(user1);
                }
            }
        }
    }
}
