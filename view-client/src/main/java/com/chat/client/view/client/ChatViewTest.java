package com.chat.client.view.client;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.net.URL;

public class ChatViewTest extends Application {

    private final String iconImageLoc = "https://img.icons8.com/flat_round/18/000000/chat.png";
    private Stage stage;

    @Override
    public void start(Stage stage) throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(ChatViewTest.class.getResource("/templates/user/startPage.fxml"));
        Parent root = fxmlLoader.load();
        com.chat.client.view.client.startpageController controller = fxmlLoader.getController();
        controller.setStage(stage);

        Scene scene = new Scene(root);
        this.stage = stage;
        stage.setScene(scene);

        stage.setMinWidth(700);
        stage.setMinHeight(590);

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

    public static void main(String[] args) {
        launch();
    }
}
