package com.chat.client.view.client.friend;

import com.chat.client.network.client.user.UserHandler;
import com.chat.client.network.client.user.impl.UserHandlerImpl;
import com.chat.client.view.client.chat.CellRenderer;
import com.chat.client.view.client.chat.ChatRendererwithbuttons;
import com.chat.server.model.user.User;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class AddFriend  implements Initializable {
    private ObservableList<User> allUsers= FXCollections.observableArrayList();
    @FXML
    private TextField phoneNumberSearch;
    @FXML
    private ListView usersListView;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    Search();
        //setListView(phoneNumberSearch.getText());
         // setSearchToAddNewFriends();


    }
    private void setListView(String phone) {

        //all user for testing the list view
        UserHandler userHandler = new UserHandlerImpl();
        List<User> users = userHandler.searchByPhone(phone);
        System.out.println("User is = " + users.size());

        allUsers = FXCollections.observableList(users);
        System.out.println(users.size());
        usersListView.setItems(allUsers);
        usersListView.setCellFactory(new ChatRendererwithbuttons());
    }

    private void Search()
    {
        phoneNumberSearch.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String oldValue, String newValue) {
            if((newValue.isEmpty() || newValue == null))
            {
                usersListView.setItems(null);
            }

            else {
                setListView(newValue);
            }
            }
        });

    }






    }



