package com.chat.client.view.client.user;

import com.chat.client.controller.client.pushNotifications.PushNotificationController;
import com.chat.client.controller.client.pushNotifications.PushNotificationInterface;
import com.chat.client.controller.client.user.UserHomeInterface;
import com.chat.client.view.client.chat.CellRenderer;
import com.chat.client.view.client.chat.ChatGroupCellRenderer;
import com.chat.client.view.client.chat.RenderImage;
import com.chat.client.view.client.notification.NotificationViewListController;
import com.chat.client.view.client.notification.traynotifications.animations.AnimationType;
import com.chat.client.view.client.notification.traynotifications.notification.TrayNotification;
import com.chat.server.model.chat.ChatGroup;
import com.chat.server.model.chat.Notification;
import com.chat.server.model.chat.NotificationType;
import com.chat.server.model.user.Mode;
import com.chat.server.model.user.User;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
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
    private AnchorPane containerPane;
    @FXML
    private AnchorPane anchorPaneNotification;

    @FXML
    private ListView chatGroupList;

    @FXML
    private AnchorPane friendsAnchorPane;
    @FXML
    private AnchorPane ChatGroupAnchorPane;
    @FXML
    private Circle userImage;


    //------------------------------data section-----------------------------
    private User currentUser;
    private UserHomeInterface userHomeInterface;
    private PushNotificationController pushNotificationController;
    private NotificationViewListController notificationViewListcontroller;
    private RenderImage renderImage = new RenderImage();


    private boolean showList = true;
    private boolean isShowNotificationList;

    private ObservableList<ChatGroup> chatGroupObservableList = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
       Platform.runLater(this::loadNotificationList);
    }

    public UserViewHome() {
        try {
            pushNotificationController = new PushNotificationController();
            pushNotificationController.setPushNotifications(this);
        } catch (RemoteException e) {
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

    //todo show list of requests
    @FXML
    private void onRequestsClicked(MouseEvent mouseEvent) {

    }

    /**
     * show my profile
     * @param mouseEvent
     */
    @FXML
    private void onProfileClicked(MouseEvent mouseEvent) {
        Parent root = loadUserProfile(currentUser);
        containerPane.getChildren().setAll(root);
    }

    //todo show notification list
    @FXML
    private void showNotificationList(ActionEvent actionEvent) {
        anchorPaneNotification.setVisible(!isShowNotificationList);
        isShowNotificationList = !isShowNotificationList;
    }

    //todo logout
    @FXML
    private void logoOut(MouseEvent mouseEvent) {
        logout(currentUser);
    }

    //todo show friend list
    @FXML
    private void viewFriendList(ActionEvent actionEvent) {

    }


    @FXML
    private void onChatGroupListClicked(MouseEvent mouseEvent) {

    }

    //------------------------------------------------------------------------------
    //-----------------------------view section-------------------------------------
    //------------------------------------------------------------------------------

    public Parent loadUserProfile(User user){

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

   /* private void setChatGroupListView(List<ChatGroup> chatGroups) {

      chatGroupObservableList = FXCollections.observableList(chatGroups);

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
    }*/


    //------------------------------------------------------------------------------
    //----------------------------Notification Section -----------------------------
    //------------------------------------------------------------------------------

    @Override
    public void receiveNotification(Notification notification) {
        Platform.runLater(() ->
        {
           // Image profileImg = new Image(getClass().getResource("/static/images/AddPic.png").toString(), 50, 50, false, false);
            Image userImage = renderImage.convertToImage(notification.getUserFrom().getImage(), 22, 22, false, false);

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
            anchorPaneNotification.getChildren().setAll(load);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    //------------------------------------------------------------------------------
    //--------------------------setter section--------------------------------------
    //------------------------------------------------------------------------------

    /**
     * set current user data
     * @param currentUser
     */
    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }

    /**
     * set home controller reference
     * @param userHomeInterface object from user home controller
     */
    public void setUserHomeInterface(UserHomeInterface userHomeInterface) {
        this.userHomeInterface = userHomeInterface;
    }
}
