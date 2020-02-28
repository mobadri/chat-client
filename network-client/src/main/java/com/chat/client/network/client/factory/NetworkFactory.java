package com.chat.client.network.client.factory;

import com.chat.client.network.client.chat.ChatGroupHandler;
import com.chat.client.network.client.chat.MessageHandler;
import com.chat.client.network.client.chat.impl.ChatGroupHandlerImpl;
import com.chat.client.network.client.chat.impl.MessageHandlerImpl;
import com.chat.client.network.client.notifocation.NotificationHandler;
import com.chat.client.network.client.notifocation.impl.NotificationHandlerImpl;
import com.chat.client.network.client.socket_factory.SslClientSocketFactory;
import com.chat.client.network.client.user.FileTransferHandeler;
import com.chat.client.network.client.user.FileTransferHandeler;
import com.chat.client.network.client.user.UserHandler;
import com.chat.client.network.client.user.impl.FileTranseferHandlerImpl;
import com.chat.client.network.client.user.impl.UserHandlerImpl;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.util.Objects;

/**
 * factory class to create object for that layer
 */
public class NetworkFactory {
    private static UserHandler userHandler;
    private static ChatGroupHandler chatGroupHandler;
    private static MessageHandler messageHandler;
    private static NotificationHandler notificationHandler;
    private static SslClientSocketFactory sslClientSocketFactory;
    private static FileTransferHandeler fileTransferHandeler;

    private NetworkFactory() {
    }

    /**
     * create UserHandler Object for user over application
     *
     * @return UserHandler implantation
     */
    public static UserHandler createUserHandler() {
        userHandler = Objects.requireNonNullElseGet(userHandler, () -> new UserHandlerImpl());
        return NetworkFactory.userHandler;
    }

    /**
     * create ChatGroupHandler Object for user over application
     *
     * @return ChatGroupHandler implantation
     */
    public static ChatGroupHandler createChatGroupHandler() {
        chatGroupHandler = Objects.requireNonNullElseGet(chatGroupHandler, () -> new ChatGroupHandlerImpl());
        return chatGroupHandler;
    }

    /**
     * create NotificationHandler Object for user over application
     *
     * @return NotificationHandlerImplantation
     */
    public static synchronized NotificationHandler createNotificationHandler() {

        if (notificationHandler == null)
            notificationHandler = new NotificationHandlerImpl();
        return notificationHandler;
    }

    public static SslClientSocketFactory createSslClientSocketFactory() {
        if (sslClientSocketFactory == null) {
            try {
                //todo encrypt password
                sslClientSocketFactory = new SslClientSocketFactory("security/client", "ahm741741");
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            } catch (KeyManagementException e) {
                e.printStackTrace();
            } catch (CertificateException e) {
                e.printStackTrace();
            } catch (KeyStoreException e) {
                e.printStackTrace();
            } catch (UnrecoverableKeyException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return sslClientSocketFactory;
    }

    /**
     * create MessageHandler Object for user over application
     *
     * @return MessageHandlerImplantation
     */

    public static synchronized MessageHandler createMessageHandler() {

        if (messageHandler == null)
            messageHandler = new MessageHandlerImpl();
        return messageHandler;
    }

    /**
     * createFileTransferHandeler Object for user over application
     *
     * @return FileTransferHandelerImplantation
     */
    public static FileTransferHandeler createFileTransferHandler() {

        if (fileTransferHandeler == null)
            fileTransferHandeler = new FileTranseferHandlerImpl();
        return fileTransferHandeler;
    }
}
