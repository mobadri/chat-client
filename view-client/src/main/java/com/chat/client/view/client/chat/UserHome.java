package com.chat.client.view.client.chat;

import com.chat.client.controller.client.chatGroup.ChatGroupController;
import com.chat.client.controller.client.pushNotifications.PushNotificationController;
import com.chat.client.controller.client.pushNotifications.PushNotificationInterface;
import com.chat.client.controller.client.user.HomeController;
import com.chat.client.network.client.user.UserHandler;
import com.chat.client.network.client.user.impl.UserHandlerImpl;
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
    private AnchorPane containerPane;
    ListProperty<User> myFriendsListProperty = new SimpleListProperty<>();
    private ObservableList<User> myFriendsList = FXCollections.observableArrayList();
    Stage friendStage;


    //app controller
    private ChatGroupController chatGroupInterface;
    private PushNotificationController pushNotificationController;
    private User currrentUser;

    private Stage stage;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        setListView();
        setSearchforfriends();
    }

    void setSearchforfriends() {
        FilteredList<User> filteredData = new FilteredList<>(myFriendsList, p -> true);
        searchTextListner(filteredData);
        SortedList<User> sortedData = new SortedList<>(filteredData);
        userList.setItems(sortedData);
    }

    public UserHome() {
        try {
            chatGroupInterface = new ChatGroupController();
            pushNotificationController = new PushNotificationController();
            pushNotificationController.setPushNotifications(this);
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

    private void setListView() {
        //@shaheen
        //todo change the list user friends
        //all user for testing the list view
        UserHandler userHandler = new UserHandlerImpl();
        List<User> users = userHandler.getAllUsers();
        myFriendsList = FXCollections.observableList(users);
        System.out.println(users.size());
        userList.setItems(myFriendsList);
        userList.setCellFactory(new CellRenderer());
    }

    @FXML
    private void onFriendsListClicked(MouseEvent mouseEvent) {
        User user = (User) userList.getSelectionModel().getSelectedItem();
        if (user != null) {
            addFriend(user);
            loadFriendProfile(user);
//            loadChatGroup(new ChatGroup());
        }
    }

    private void loadFriendProfile(User user) {
        try {
            FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/templates/user/User_profile.fxml"));
            Parent root = loader.load();
            UserProfileController userProfileController = loader.getController();
            userProfileController.setUser(user);
            System.out.println("my Profile is loaded ............");
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
            chatViewController.setUser(currrentUser);
            //@yasmine
            //todo don't forget to add groupchat to chatviewcontroller
            //---------
            //add ref to each other
            chatGroupInterface.setChatGroupInterface(chatViewController);
            chatViewController.setChatGroupInterface(chatGroupInterface);

//                containerPane.getChildren().add(root);

            AnchorPane child = new AnchorPane(root);
            AnchorPane.setTopAnchor(child, 10.0);
            AnchorPane.setBottomAnchor(child, 10.0);
            AnchorPane.setLeftAnchor(child, 10.0);
            AnchorPane.setRightAnchor(child, 10.0);
            containerPane.getChildren().setAll((AnchorPane) child);


//                containerPane = new AnchorPane(root);
//                content = (AnchorPane) FXMLLoader.load("vista2.fxml");


        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void setCurrrentUser(User currrentUser) {
        this.currrentUser = currrentUser;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
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
        homeController.addFriend(currrentUser, friend);
    }

    @FXML
    public void addFriend(MouseEvent mouseEvent) {
        System.out.println("Hello i'm here Add new Friend");
        Parent root;
        try {
            System.out.println("I'm Here to add friend");
            FXMLLoader loader =
                    new FXMLLoader(getClass().getResource("/templates/friend/addFriend.fxml"));
            System.out.println(getClass().getResource("/templates/friend/addFriend.fxml").getPath());
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
}
