package com.chat.client.view.client.chat;

import com.chat.client.controller.client.chatGroup.ChatGroupController;
import com.chat.client.controller.client.pushNotifications.PushNotificationController;
import com.chat.client.controller.client.pushNotifications.PushNotificationInterface;
import com.chat.client.controller.client.user.HomeController;
import com.chat.client.view.client.chat.render.CellRenderer;
import com.chat.client.view.client.chat.render.ChatGroupCellRenderer;
import com.chat.client.view.client.chat.render.RenderImage;
import com.chat.client.view.client.friend.AddFriend;
import com.chat.client.view.client.login.LoginViewController;
import com.chat.client.view.client.notification.FriendRequestListViewController;
import com.chat.client.view.client.notification.NotificationViewListController;
import com.chat.client.view.client.notification.traynotifications.animations.AnimationType;
import com.chat.client.view.client.notification.traynotifications.notification.TrayNotification;
import com.chat.client.view.client.user.UserProfileController;
import com.chat.server.model.chat.ChatGroup;
import com.chat.server.model.chat.Notification;
import com.chat.server.model.chat.NotificationType;
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
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class UserHome implements Initializable, PushNotificationInterface {
    @FXML
    public Label userName;
    @FXML
    private TextField searchforfriends;
    @FXML
    private Button addFriend;
    RenderImage renderImage = new RenderImage();
    NotificationViewListController notificationViewListcontroller;
    FriendRequestListViewController friendRequestListViewController;

    @FXML
    private ListView userList;
    @FXML
    private ListView chatGroupList;
    @FXML
    private StackPane containerPane;
    @FXML
    private AnchorPane friendsAnchorPane;
    @FXML
    private AnchorPane ChatGroupAnchorPane;
    @FXML
    private StackPane anchorPaneNotification;
    @FXML
    private Circle userImage;
    @FXML
    private ImageView defaultUserImage;
    private boolean showList = true;
    ListProperty<User> myFriendsListProperty = new SimpleListProperty<>();
    private ObservableList<User> myFriendsList = FXCollections.observableArrayList();

    private ObservableList<ChatGroup> myChatGroupsList = FXCollections.observableArrayList();

    private ObservableList<Notification> myNotificationList = FXCollections.observableArrayList();
    private List<ChatGroupController> chatGroupControllerList = new ArrayList<>();
    private List<Parent> chatViewList = new ArrayList<>();
    private HomeController homeController;


    Stage friendStage;


    private PushNotificationController pushNotificationController;
    private User currentUser;

    private boolean isShowNotificationList = false;
    private boolean isShowFriendRequestList = false;
    private Parent firendRequestPane;
    private Parent notificationPane;


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

        loadNotificationList();
        loadFriendRequestList();
    }

    public void nav(MouseEvent mouseEvent) {
    }

    public void logOut(MouseEvent mouseEvent) {
        File file = new File("userInfo.xml");
        if (file.exists()) file.delete();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/templates/login/login.fxml"));
        try {
            Parent root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        LoginViewController loginView = loader.getController();
        loginView.setStageLogin(friendStage);
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
            Platform.runLater(() -> {
                setUserDataView(currentUser);

            });
        }).start();
        new Thread(() -> {
            setFriendsListView(currentUser.getFriends());
        }).start();
        new Thread(() -> {
            setChatGroupListView(currentUser.getChatGroups());
        }).start();


        setSearchforfriends();
    }

    private void setUserDataView(User user) {
        Image image = renderImage.convertToImage(user.getImage());
        if (image != null) {
            userImage.setFill(new ImagePattern(image));
        }
        defaultUserImage.setVisible(false);
        userName.setText(user.getFirstName() + " " + user.getLastName());

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
            tray.setAnimationType(AnimationType.FADE);
            tray.setImage(profileImg);
            tray.showAndDismiss(Duration.seconds(5));
            tray.setNotificationType(NotificationType.MESSAGE_RECEIVED);
            addNotificationToList(notification);
            System.out.println("send proplem");
        });
        System.out.println("send proplem");
    }

    private void addNotificationToList(Notification notification) {
        notificationViewListcontroller.addNotification(notification);
    }

    @FXML
    public void showNotification(ActionEvent actionEvent) {
        anchorPaneNotification.setVisible(!isShowNotificationList);
        isShowNotificationList = !isShowNotificationList;
        anchorPaneNotification.getChildren().clear();
        anchorPaneNotification.getChildren().setAll(notificationPane);

    }

    private void loadNotificationList() {
        try {
            FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/templates/notification/notification-list.fxml"));
            notificationPane = loader.load();
            notificationViewListcontroller = loader.getController();

        } catch (IOException e) {
            e.printStackTrace();
        }

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
            AddFriend controller = loader.getController();
//            controller.setHomeController(this);
            controller.setCurrentUser(currentUser);
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

    @Override
    public void changeFriendsStatus(User user) {

        pushNotificationController.changeFriendsStatus(user);
    }

    @Override
    public void showOfflineFriends(User user) {

        pushNotificationController.showOfflineFriends(user);
    }

    private void addfriendRequestToList(User user) {

        friendRequestListViewController.addFriendRequestequest(user);
    }

    @FXML
    public void handleRequestsButton(ActionEvent actionEvent) {
        System.out.println("isShowFriendRequestList");
        anchorPaneNotification.setVisible(!isShowFriendRequestList);
        isShowFriendRequestList = !isShowFriendRequestList;
        anchorPaneNotification.getChildren().clear();
        anchorPaneNotification.getChildren().setAll(firendRequestPane);
    }

    private void loadFriendRequestList() {
        try {
            FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/templates/notification/FriendRequest-list.fxml"));
            firendRequestPane = loader.load();
            friendRequestListViewController = loader.getController();
            List<User> friendRequest = homeController.getFriendRequest(currentUser);
            for (User user : friendRequest) {
                friendRequestListViewController.addFriendRequestequest(user);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    public void onFriendsListClicked(MouseEvent mouseEvent) {

    }
}