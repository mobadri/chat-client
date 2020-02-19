package com.chat.client.controller.client.user.login;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;


public class RememberMeController {
    Document document;
    DocumentBuilder documentBuilder;
    DocumentBuilderFactory documentFactory;

    /* public static void main(String[] args) {
         RememberMeController r = new RememberMeController();
         r.CreateIntoXML("01002583199", "mariam");
     }
 */
    public Document createDocument() {
        try {
            documentFactory = DocumentBuilderFactory.newInstance();
            documentBuilder = documentFactory.newDocumentBuilder();
            document = documentBuilder.newDocument();
            System.out.println(document);
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }
        return document;
    }


    public Document writeInMemory(String phone, String password) {
        document = createDocument();
        documentFactory = DocumentBuilderFactory.newInstance();
        try {
            documentBuilder = documentFactory.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }
        document = documentBuilder.newDocument();
        System.out.println(document);

        Element user = document.createElement("User");

        Element userPhone = document.createElement("phone");
        userPhone.appendChild(document.createTextNode(phone));
        user.appendChild(userPhone);

        Element userPassword = document.createElement("password");
        userPassword.appendChild(document.createTextNode(password));
        user.appendChild(userPassword);
        document.appendChild(user);
        System.out.println(document.getTextContent());
        System.out.println(user.getElementsByTagName("user").getLength());
        return document;
    }

    public void CreateIntoXML(String phone, String password) {

        try {

            document = writeInMemory(phone, password);
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            System.out.println(document);
            DOMSource domSource = new DOMSource(document);
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            File file = new File("userInfo.xml");
            StreamResult streamResult = new StreamResult(file);
            System.out.println(streamResult);
            transformer.transform(domSource, streamResult);
        } catch (TransformerException e) {
            e.printStackTrace();
        }
    }
}
