package com.chat.client.network.client.config;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;

public class NetworkConfig {
    private static NetworkConfig instance;
    private String serverIp;
    private int serverPortNumber;

    private NetworkConfig() {
        DocumentBuilderFactory documentFactory = DocumentBuilderFactory.newInstance();
        try {
            DocumentBuilder documentBuilder = documentFactory.newDocumentBuilder();
            Document document = documentBuilder.parse(new File("networkConfig.xml"));
            document.getDocumentElement().normalize();
            NodeList networkConfig1 = document.getElementsByTagName("networkConfig");
            for(int i=0;i<networkConfig1.getLength();i++){
                Node item = networkConfig1.item(i);
                Element eElement = (Element) item;
                this.serverIp = eElement.getElementsByTagName("server-ip").item(i).getTextContent().trim();
                String trim = eElement.getElementsByTagName("server-port").item(i).getTextContent().trim();
                this.serverPortNumber = Integer.valueOf(trim);
            }
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }
    }

    public String getServerIp() {
        return serverIp;
    }

    public int getServerPortNumber() {
        return serverPortNumber;
    }

    public synchronized static NetworkConfig getInstance(){
        if(instance == null){
            return new NetworkConfig();
        }
        return instance;
    }
}
