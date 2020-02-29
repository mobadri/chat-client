package com.chat.client.network.client.notifocation.impl;

import com.chat.client.network.client.config.NetworkConfig;
import com.chat.client.network.client.factory.NetworkFactory;
import com.chat.client.network.client.notifocation.NotificationHandler;
import com.chat.client.service.client.callback.NotificationServiceCallback;
import com.chat.server.model.chat.Notification;
import com.chat.server.model.chat.NotificationType;
import com.chat.server.model.user.User;
import com.chat.server.service.server.notification.ServerNotificationService;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.List;

//import com.chat.client.service.client.factory.ServiceClientFactory;
//import com.chat.client.service.client.notification.ClientNotificationService;

public class NotificationHandlerImpl implements NotificationHandler {

    NetworkConfig networkConfig;
    ServerNotificationService notificationService;

    public NotificationHandlerImpl() {
        networkConfig = NetworkConfig.getInstance();
        int portNumber = networkConfig.getServerPortNumber();
        String serverIP = networkConfig.getServerIp();
        try {
//            Registry registry = LocateRegistry.getRegistry(serverIP, portNumber);
            Registry registry = LocateRegistry.getRegistry(serverIP,
                    portNumber/*, NetworkFactory.createSslClientSocketFactory()*/);

            /*commented segments of code is connection security trail */
           /* Registry registry = LocateRegistry.getRegistry(InetAddress.getLocalHost().getHostName(),
                    PORT_NUMBER, new RMISSLClientSocketFactory());*/
//            Registry registry = LocateRegistry.getRegistry(PORT_NUMBER);
            notificationService = (ServerNotificationService) registry.lookup("notificationService");

        } catch (RemoteException | NotBoundException e) {
            e.printStackTrace();
            System.out.println("something incorrect happened!!");
        }
    }

    @Override
    public List<Notification> getUserNotification(User user, boolean seen) {

        List<Notification> notifications = new ArrayList<>();
        try {
            notifications = notificationService.getUserNotification(user, seen);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return notifications;
    }

    @Override
    public List<Notification> getUserNotificationByType(User user, boolean seen, NotificationType notificationType) {

        List<Notification> notifications = new ArrayList<>();
        try {
            notifications = notificationService.getUserNotificationByType(user, seen, notificationType);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return notifications;
    }

    @Override
    public void register(NotificationServiceCallback notificationServiceCallback) {
        try {
            notificationService.register(notificationServiceCallback);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void unregister(NotificationServiceCallback notificationServiceCallback) {
        try {
            notificationService.unregister(notificationServiceCallback);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }
}