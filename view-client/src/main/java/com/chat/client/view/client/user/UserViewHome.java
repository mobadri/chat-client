package com.chat.client.view.client.user;

import com.chat.client.controller.client.chatGroup.ChatGroupController;
import com.chat.client.controller.client.fileTransfer.FileTranseferControllerImpl;
import com.chat.client.controller.client.pushNotifications.PushNotificationController;
import com.chat.client.controller.client.pushNotifications.PushNotificationInterface;
import com.chat.client.controller.client.user.HomeController;
import com.chat.client.controller.client.user.UserHomeInterface;
import com.chat.client.view.client.chat.ChatGroupListViewController;
import com.chat.client.view.client.chat.ChatViewController;
import com.chat.client.view.client.chat.render.ChatGroupCellRenderer;
import com.chat.client.view.client.chat.render.RenderImage;
import com.chat.client.view.client.notification.FriendRequestListViewController;
import com.chat.client.view.client.notification.NotificationViewListController;
import com.chat.client.view.client.notification.traynotifications.animations.AnimationType;
import com.chat.client.view.client.notification.traynotifications.notification.TrayNotification;
import com.chat.server.model.chat.ChatGroup;
import com.chat.server.model.chat.Notification;
import com.chat.server.model.chat.NotificationType;
import com.chat.server.model.user.FriendStatus;
import com.chat.server.model.user.Mode;
import com.chat.server.model.user.User;
import com.jfoenix.controls.JFXListView;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class UserViewHome implements Initializable, UserHomeInterface, PushNotificationInterface {

    //------------------------------view section-----------------------------
    @FXML
    private StackPane containerPane;
    @FXML
    private AnchorPane anchorPaneNotification;
    @FXML
    private AnchorPane ChatGroupAnchorPane;
    @FXML
    private Circle userImage;
    @FXML
    private ImageView defaultUserImage;
    @FXML
    public Label userName;

    FriendRequestListViewController friendRequestListViewController;
    private boolean isShowFriendRequestList = false;
    private Parent firendRequestPane;
    private Parent notificationPane;

    //------------------------------data section-----------------------------
    private User currentUser;
    private UserHomeInterface userHomeInterface;
    private PushNotificationController pushNotificationController;
    private NotificationViewListController notificationViewListcontroller;
    private ChatGroupListViewController chatGroupListViewController;
    private UserFriends userFriendsController;
    private HomeController homeController;
    private RenderImage renderImage = new RenderImage();


    private boolean showList = true;
    private boolean isShowNotificationList;
    private Parent friendsPane;
    private Parent groupsPane;

    private List<Parent> chatViewList = new ArrayList<>();
    private List<ChatGroupController> chatGroupControllerList = new ArrayList<>();

    private ObservableList<ChatGroup> chatGroupObservableList = FXCollections.observableArrayList();
    private FileTranseferControllerImpl fileTranseferController;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Platform.runLater(this::loadNotificationList);
        Platform.runLater(this::loadFriendRequestList);
        //loadFriendRequestList();
    }

    public UserViewHome() {
        try {
            fileTranseferController = new FileTranseferControllerImpl();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    private void loadFriendsListView() {
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/templates/user/user-friends.fxml"));
        try {
            friendsPane = loader.load();
            userFriendsController = loader.getController();
            userFriendsController.setCurrentUser(currentUser);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadChatGroupListView() {
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/templates/chat/chat-group-listview.fxml"));
        try {
            groupsPane = loader.load();
            chatGroupListViewController = loader.getController();
            chatGroupListViewController.setCurrentUser(currentUser);
            chatGroupListViewController.setUserHome(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void logout(User currentUser) {
        userHomeInterface.logout(currentUser);
    }

    @Override
    public void changeMode(User currentUser, Mode mode) {
        userHomeInterface.changeMode(currentUser, mode);
    }

    @Override
    public void appendFriend(User friend) {
        userHomeInterface.appendFriend(friend);
    }

    @Override
    public void appendChatGroup(ChatGroup chatGroup) {
        userHomeInterface.appendChatGroup(chatGroup);
    }

    @Override
    public List<User> getAllFriends(User currentUser) {
        return userHomeInterface.getAllFriends(currentUser);
    }

    @Override
    public List<ChatGroup> getAllChatGroups(User currentUser) {
        return userHomeInterface.getAllChatGroups(currentUser);
    }

    @Override
    public ChatGroup addFriendToChatGroup(ChatGroup chatGroup, User user) {
        return userHomeInterface.addFriendToChatGroup(chatGroup, user);
    }

    //todo show list of requests
    @FXML
    private void onRequestsClicked(MouseEvent mouseEvent) {

    }

    /**
     * show my profile
     *
     * @param mouseEvent
     */
    @FXML
    private void onProfileClicked(MouseEvent mouseEvent) {
        Parent root = loadUserProfile(currentUser);
        containerPane.getChildren().setAll(root);
    }

    @FXML
    private void showNotificationList(ActionEvent actionEvent) {
        anchorPaneNotification.setVisible(!isShowNotificationList);
        isShowNotificationList = !isShowNotificationList;
    }

    @FXML
    private void logoOut(MouseEvent mouseEvent) {
        logout(currentUser);
    }

    @FXML
    private void viewFriendList(ActionEvent actionEvent) {
        ChatGroupAnchorPane.getChildren().clear();
        ChatGroupAnchorPane.getChildren().add(friendsPane);
    }

    @FXML
    private void viewChatGroupsList(ActionEvent actionEvent) {
        ChatGroupAnchorPane.getChildren().clear();
        ChatGroupAnchorPane.getChildren().add(groupsPane);
    }

    @FXML
    private void handleRequestsButton(ActionEvent actionEvent) {
        anchorPaneNotification.setVisible(!isShowFriendRequestList);
        isShowFriendRequestList = !isShowFriendRequestList;
        anchorPaneNotification.getChildren().clear();
        anchorPaneNotification.getChildren().setAll(firendRequestPane);
    }

    //------------------------------------------------------------------------------
    //-----------------------------view section-------------------------------------
    //------------------------------------------------------------------------------

    public Parent loadUserProfile(User user) {

        try {
            FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/templates/user/User_profile.fxml"));
            Parent root = loader.load();
            UserProfileController userProfileController = loader.getController();
            userProfileController.setUser(user);
            return root;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private void loadChatGroup(ChatGroup chatGroup) {

        try {
            FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/templates/chat/chat-view.fxml"));
            Parent root = loader.load();
            //view controller
            ChatViewController chatViewController = loader.getController();

            fileTranseferController.setCurrentUser(currentUser);
            chatViewController.setFileTranseferController(fileTranseferController);

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

    private void loadAllChatGroupViewOnMemory(ObservableList<ChatGroup> myChatGroupsList) {
        for (ChatGroup chatGroup : myChatGroupsList) {
            System.out.println(chatGroup.getId());
            loadChatGroup(chatGroup);
        }
    }
    //------------------------------------------------------------------------------
    //----------------------------Notification Section -----------------------------
    //------------------------------------------------------------------------------

    @Override
    public void receiveNotification(Notification notification) {
        Platform.runLater(() ->
        {
            Image userImage = renderImage.convertToImage(notification.getUserFrom().getImage());
            TrayNotification tray = new TrayNotification();
            tray.setTitle(notification.getNotificationType().toString());
            tray.setMessage(notification.getNotificationMessage());
            tray.setRectangleFill(Paint.valueOf("#2C3E50"));
            tray.setAnimationType(AnimationType.FADE);
            tray.setImage(userImage);
            tray.showAndDismiss(Duration.seconds(5));
            tray.setNotificationType(NotificationType.MESSAGE_RECEIVED);
            addNotificationToList(notification);
        });

    }

    @Override
    public void changeFriendsStatus(User user) {

    }

    @Override
    public void showOfflineFriends(User user) {

    }

    private void addNotificationToList(Notification notification) {
        notificationViewListcontroller.addNotification(notification);
    }

    private void loadNotificationList() {
        try {
            FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/templates/notification/notification-list.fxml"));
            Parent load = loader.load();
            notificationViewListcontroller = loader.getController();
            notificationViewListcontroller.setUserHome(this);
            anchorPaneNotification.getChildren().setAll(load);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    //------------------------------------------------------------------------------
    //--------------------------setter section--------------------------------------
    //------------------------------------------------------------------------------

    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
        try {
            pushNotificationController = new PushNotificationController();
            pushNotificationController.setPushNotifications(this);
            homeController = new HomeController();
            pushNotificationController.setCurrentUser(currentUser);
        } catch (IOException e) {
            e.printStackTrace();
        }
        loadFriendsListView();
        loadChatGroupListView();

        new Thread(() -> {
            Platform.runLater(() -> {
                setUserDataView(currentUser);

            });
        }).start();
        new Thread(() -> {
            setChatGroupListView(currentUser.getChatGroups());
        }).start();

    }

    private void setUserDataView(User user) {
        Image image = renderImage.convertToImage(user.getImage());
        if (image != null) {
            userImage.setFill(new ImagePattern(image));
        }
        defaultUserImage.setVisible(false);
        userName.setText(user.getFirstName() + " " + user.getLastName());

    }

    private void setChatGroupListView(List<ChatGroup> chatGroups) {
        chatGroupObservableList = FXCollections.observableList(chatGroups);
        JFXListView<ChatGroup> chatGroupListView = chatGroupListViewController.getChatGroupListView();

        new Thread(() -> {
            List<ChatGroup> groups = new ArrayList<>();
            for (ChatGroup chatGroup : chatGroups) {
                groups.add(homeController.getById(chatGroup.getId()));
            }

            Platform.runLater(() -> {
                chatGroupObservableList = FXCollections.observableList(groups);
                chatGroupListView.setItems(chatGroupObservableList);
                loadAllChatGroupViewOnMemory(chatGroupObservableList);
            });
        }).start();
        chatGroupListView.setItems(chatGroupObservableList);
        chatGroupListView.setCellFactory(new ChatGroupCellRenderer());
    }

    /**
     * set current user data
     *
     * @param currentUser
     */

    /**
     * set home controller reference
     *
     * @param userHomeInterface object from user home controller
     */
    public void setUserHomeInterface(UserHomeInterface userHomeInterface) {
        this.userHomeInterface = userHomeInterface;
    }

    public ObservableList<ChatGroup> getChatGroupObservableList() {
        return chatGroupObservableList;
    }

    public void setOnMainPane(Parent node) {
        containerPane.getChildren().setAll(node);
    }

    public void clientAcceptFile(String fileName, int chatGroupId, User userTo) {
        for (ChatGroup chatGroup : chatGroupObservableList) {
            if(chatGroup.getId() == chatGroupId){
                fileTranseferController.clientAcceptFile(fileName, chatGroupId, userTo);
            }
        }
    }


    public List<Parent> getChatViewList() {
        return chatViewList;
    }


    private void addfriendRequestToList(User user) {
        friendRequestListViewController.addFriendRequestequest(user);
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

    public List<User> findByPhone(String phone) {
        return homeController.findByPhone(phone);
    }

    public FriendStatus getFriendStatus(User user, User friend) {
        return homeController.getSatatus(user.getId(), friend.getId());
    }

    public void removeFriend(User user, User userFriend) {
        homeController.removeFriend(user, userFriend);
    }

    public void addFriend(User user, User friend) {
        homeController.addFriend(user, friend);
    }

    public void updateFriend(User user, User friend, FriendStatus status) {
        homeController.updateFriend(user.getId(), friend.getId(), status);
    }
}