package com.chat.client.view.client;

import com.chat.client.controller.client.user.login.RegistrationController;
import com.chat.client.controller.client.user.login.SignUpAndRegistration;
import com.chat.client.view.client.login.LoginViewController;
import com.chat.server.model.user.User;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.imageio.ImageIO;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.net.URL;

public class App extends Application {

    private final String iconImageLoc = "https://img.icons8.com/office/16/000000/chat.png";
    private double xOffset;
    private double yOffset;
    private Stage stage;

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
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/templates/login/test.fxml"));
        Parent root = loader.load();
        root.setOnMouseClicked(mouseEvent -> {
            xOffset = mouseEvent.getSceneX();
            yOffset = mouseEvent.getSceneY();

        });
        root.setOnMouseDragged(mouseEvent -> {
            primaryStage.setX(mouseEvent.getScreenX() - xOffset);
            primaryStage.setY(mouseEvent.getScreenY() - yOffset);
        });

        LoginViewController controller = loader.getController();
        controller.setStageLogin(primaryStage);
        controller.setSignUpAndRegistration(new RegistrationController());
        controller.setStageLogin(primaryStage);

        Scene scene = new Scene(root);
        scene.setFill(Color.TRANSPARENT);
        primaryStage.setResizable(false);
        primaryStage.setScene(scene);
        primaryStage.show();

        stage = primaryStage;
        stage.setOnCloseRequest(event -> {
            // instructs the javafx system not to exit implicitly when the last application window is shut.
            Platform.setImplicitExit(false);
            // sets up the tray icon (using awt code run on the swing thread).
            javax.swing.SwingUtilities.invokeLater(this::addAppToTray);
        });
        // out stage will be translucent, so give it a transparent style.
        stage.show();
    }

    private void showStage() {
        if (stage != null) {
            stage.show();
            stage.toFront();
        }
    }

    private void addAppToTray() {

        try {
            // ensure awt toolkit is initialized.
            java.awt.Toolkit.getDefaultToolkit();

            // app requires system tray support, just exit if there is no support.
            if (!java.awt.SystemTray.isSupported()) {
                System.out.println("No system tray support, application exiting.");
                Platform.exit();
            }

            // set up a system tray icon.
            java.awt.SystemTray tray = java.awt.SystemTray.getSystemTray();
            URL imageLoc = new URL(iconImageLoc);
            java.awt.Image image = ImageIO.read(imageLoc);
            java.awt.TrayIcon trayIcon = new java.awt.TrayIcon(image);

            // if the user double-clicks on the tray icon, show the main app stage.
            trayIcon.addActionListener(event -> {
                tray.remove(trayIcon);
                Platform.runLater(this::showStage);
            });

            // if the user selects the default menu item (which includes the app name),
            // show the main app stage.
            java.awt.MenuItem openItem = new java.awt.MenuItem("Open");
            openItem.addActionListener(event -> {
                tray.remove(trayIcon);
                Platform.runLater(this::showStage);
            });

            // the convention for tray icons seems to be to set the default icon for opening
            // the application stage in a bold font.
            java.awt.Font defaultFont = java.awt.Font.decode(null);
            java.awt.Font boldFont = defaultFont.deriveFont(java.awt.Font.BOLD);
            openItem.setFont(boldFont);

            // to exit from application
            java.awt.MenuItem exitItem = new java.awt.MenuItem("Exit");
            exitItem.addActionListener(event -> {
                Platform.exit();
                tray.remove(trayIcon);
                System.exit(0);
            });

            // setup the popup menu for the application.
            final java.awt.PopupMenu popup = new java.awt.PopupMenu();
            popup.add(openItem);
            popup.addSeparator();
            popup.add(exitItem);
            trayIcon.setPopupMenu(popup);

            // create a timer which periodically displays a notification message.

            // add the application tray icon to the system tray.
            tray.add(trayIcon);
        } catch (java.awt.AWTException | IOException e) {
            System.out.println("Unable to init system tray");
            e.printStackTrace();
        }
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
        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
        }
    }

    private void loadLoginPage(Stage stage) {
        startpageController startpageController = new startpageController();
        startpageController.setStage(stage);
        stage.show();
        startpageController.goToSignInPage(new ActionEvent());
    }
}
