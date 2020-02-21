package com.chat.client.view.client;

import com.chat.client.controller.client.user.login.RegistrationController;
import com.chat.client.controller.client.user.login.SignUpAndRegistration;
import com.chat.client.view.client.login.LoginViewController;
import com.chat.server.model.user.User;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;

public class Main extends Application {

    public static void main(String[] args) {

        launch(args);

    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        File file = new File("userInfo.xml");
      /*  if (file.exists()) {
            rememberMeHomePage(file, primaryStage);
        } else {
*/
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/templates/user/startPage.fxml"));
            Parent root = loader.load();
            com.chat.client.view.client.startpageController controller = loader.getController();
            controller.setStage(primaryStage);
            Scene scene = new Scene(root);
            scene.setFill(Color.TRANSPARENT);
            primaryStage.setScene(scene);
            primaryStage.show();
      //  }

    }

    public void rememberMeHomePage(File file, Stage primaryStage) {

        LoginViewController loginViewController = new LoginViewController();
        DocumentBuilderFactory documentFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder = null;
        try {
            documentBuilder = documentFactory.newDocumentBuilder();

            Document document = documentBuilder.parse(file);
            NodeList userInfo = document.getElementsByTagName("User");

            String data = userInfo.item(0).getTextContent();
            System.out.println(data);

            String phone = data.trim().substring(0, 11);
            String password = data.trim().substring(13).trim();
            SignUpAndRegistration signUpAndRegistration = new RegistrationController();
            User user = signUpAndRegistration.login(phone, password);
            if (user != null) {
                System.out.println("PHONE : " + phone);
                System.out.println("PASSWORD " + password);

                loginViewController.setStageLogin(primaryStage);
                primaryStage.show();
                loginViewController.goToHomePage(user);
            } else {
                loadLoginPage(primaryStage);
            }
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadLoginPage(Stage stage) {
        startpageController startpageController = new startpageController();
        startpageController.setStage(stage);
        stage.show();
        startpageController.gotosigninpage(new ActionEvent());
    }
}
