package com.chat.client.service.client.callback;

import javax.net.ssl.*;
import java.io.*;
import java.net.Socket;
import java.rmi.server.RMIClientSocketFactory;
import java.security.*;
import java.security.cert.CertificateException;

/**
 * SslClientSocketFactory using (ssl and tsl)
 */
public class SslClientSocketFactory implements RMIClientSocketFactory, Serializable {

    private SSLSocketFactory sf = null;
    private static SslClientSocketFactory instance = null;

    /**
     * complete guid https://github.com/hvqzao/java-rmi
     * create SSl Client socket factory using KeyStore (ks) and  TrustManagerFactory
     * <p>
     * ssl(Secure Sockets Layer)
     * tls(Transport Layer Security)
     *
     * @param filename file path on file system
     * @param password password of keystore
     * @throws FileNotFoundException
     * @throws IOException
     * @throws NoSuchAlgorithmException
     * @throws CertificateException
     * @throws KeyStoreException
     * @throws UnrecoverableKeyException
     * @throws KeyManagementException
     */
    private SslClientSocketFactory(String filename, String password) throws FileNotFoundException, IOException, NoSuchAlgorithmException, CertificateException, KeyStoreException, UnrecoverableKeyException, KeyManagementException {
        // creating instance from keystore
        KeyStore ks = KeyStore.getInstance("jks");
        //reading key store from file system
        ks.load(new FileInputStream(new File(filename + ".ks")), password.toCharArray());
        KeyManagerFactory kmf = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
        // init KeyManagerFactory using ks and password
        kmf.init(ks, password.toCharArray());

        // creating instance from keystore
        KeyStore ts = KeyStore.getInstance("jks");
        //reading trustManager file config from file system
        ts.load(new FileInputStream(new File(filename + ".ts")), password.toCharArray());
        // create TrustManagerFactory using DefaultAlgorithm
        TrustManagerFactory tmf = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
        // init TrustManagerFactory
        tmf.init(ts);
        // using SSLContext from type TLS (Transport Layer Security)
        SSLContext sslContext = SSLContext.getInstance("TLS");
        // init context
        sslContext.init(kmf.getKeyManagers(), tmf.getTrustManagers(), new SecureRandom());
        sf = sslContext.getSocketFactory();
    }

    @Override
    public Socket createSocket(String host, int port) throws IOException {
        SSLSocket sslSock = (SSLSocket) sf.createSocket(host, port);
        return sslSock;
    }

    public static synchronized SslClientSocketFactory getInstance() {
        if (instance == null) {
            try {
                instance = new SslClientSocketFactory("security/client", "ahm741741");
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
        System.out.println(instance);
        return instance;
    }

}
