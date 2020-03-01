package chatbot.impl;

import chatbot.ChatBotInterface;
import chatbot.XmlValueTag;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.*;
import java.io.File;
import java.io.IOException;
import java.util.*;

public class ChatBot implements ChatBotInterface {

    private static ChatBot chatBotInstance;
    private Document document;
    private File xmlFile;
    private boolean defaultMessage;
    private Map<String, List<XmlValueTag>> chatBot = new TreeMap<>();

    public static ChatBot getInstance(String userPhone) throws IOException, SAXException, ParserConfigurationException, TransformerException {

        if (chatBotInstance == null)
            chatBotInstance = new ChatBot(userPhone);
        return chatBotInstance;
    }

    private ChatBot(String userPhone) throws ParserConfigurationException, SAXException, IOException, TransformerException {

        xmlFile = new File( "chat-bot/"+ userPhone + ".xml");
        new Thread(() -> {

        if(xmlFile.exists()){
            try {
                readXml(xmlFile);
            } catch (IOException | SAXException | ParserConfigurationException e) {
                e.printStackTrace();
            }
        }
        else {
            try {
                xmlFile.createNewFile();
                createNewDocument();
                writeToXmlFile(document);
            } catch (IOException | TransformerException | ParserConfigurationException e) {
                e.printStackTrace();
            }
        }}).start();
    }

    private void createNewDocument() throws ParserConfigurationException, TransformerException {

        document = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
        Element root = document.createElement("keys");
        root.setAttributeNS("http://www.w3.org/2001/XMLSchema-instance",
                "xsi:noNamespaceSchemaLocation", "chat_bot_keys.xsd");
        document.appendChild(root);

        new Thread(() -> {
            try {
                writeToXmlFile(document);
            } catch (TransformerException e) {
                e.printStackTrace();
            }
        }).start();
    }

    public void readXml(File xmlFile) throws IOException, SAXException, ParserConfigurationException {

        getDocument(xmlFile);
        NodeList keys = document.getElementsByTagName("key");
        List<XmlValueTag> xmlValueTagList;

        for (int i = 0; i < keys.getLength(); i++) {

            Node nNode = keys.item(i);
            if (nNode.getNodeType() == Node.ELEMENT_NODE) {

                Element elem = (Element) nNode;
                String name = elem.getAttribute("name");

                NodeList values = elem.getElementsByTagName("value");
                xmlValueTagList = new ArrayList<>();

                getChildNodes(xmlValueTagList, values);
                chatBot.put(name, xmlValueTagList);
            }
        }
    }

    private void getChildNodes(List<XmlValueTag> xmlValueTagList, NodeList values) {

        for (int j = 0; j < values.getLength(); j++) {

            Node value = values.item(j);
            if (value.getNodeType() == Node.ELEMENT_NODE) {

                Element element = (Element) value;
                xmlValueTagList.add(new XmlValueTag(element.getTextContent(),
                        Integer.parseInt(element.getAttribute("weight"))));
            }
        }
    }

    private void getDocument(File xmlFile) throws ParserConfigurationException, SAXException, IOException {

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = factory.newDocumentBuilder();
        document = dBuilder.parse(xmlFile);
    }

    public List<XmlValueTag> search(String key) {

        List<XmlValueTag> xmlValueTagList = new ArrayList<>();
        chatBot.keySet().parallelStream().filter((elem) -> elem.contains(key))
                .forEach((elem) -> xmlValueTagList.addAll(chatBot.get(elem)));
        return xmlValueTagList;
    }

    public void enableChatBotToLearn(String keyName, XmlValueTag valueTag) throws
            XPathExpressionException, TransformerException {

        XPathFactory factory = XPathFactory.newInstance();
        XPath path = factory.newXPath();

        String valueXpath = "/keys/key[contains(@name,'" + keyName + "')]/*/value[contains(text(),'" + valueTag.getValue() + "')]";
        XPathExpression expression = path.compile(valueXpath);
        Node keyValue = (Node) expression.evaluate(document, XPathConstants.NODE);

        if (keyValue != null) {
            updateValueWeight(keyName, valueTag, keyValue);
        } else {

            String expr = "/keys/key[contains(@name,'" + keyName + "')]/values";
            expression = path.compile(expr);
            Node keyValues = (Node) expression.evaluate(document, XPathConstants.NODE);
            if (keyValues != null) {
                addNewValueTag(keyName, valueTag, keyValues);
            } else {
                addNewKey(keyName, valueTag);
            }
        }

        new Thread(() -> {
            try {
                writeToXmlFile(document);
            } catch (TransformerException e) {
                e.printStackTrace();
            }
        }).start();
    }

    private void addNewValueTag(String keyName, XmlValueTag valueTag, Node keyValues) {

        chatBot.get(keyName).add(valueTag);
        Element newValue = getElement(valueTag, document);
        keyValues.appendChild(newValue);
    }

    private void updateValueWeight(String keyName, XmlValueTag valueTag, Node keyValue) {

        chatBot.get(keyName).parallelStream().filter((value) -> value.getValue().trim().equalsIgnoreCase(valueTag.getValue()))
                .forEach(XmlValueTag::incrementWeight);
        Node weight = keyValue.getAttributes().getNamedItem("weight");
        weight.setNodeValue(String.valueOf(Integer.parseInt(weight.getNodeValue()) + 1));
    }

    private void writeToXmlFile(Document doc) throws TransformerException {

        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        DOMSource source = new DOMSource(doc);
        StreamResult result = new StreamResult(xmlFile);
        transformer.setOutputProperty(OutputKeys.METHOD, "xml");
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "1");
        transformer.transform(source, result);
    }

    public void addNewKey(String keyName, XmlValueTag valueTag) throws TransformerException {

        List<XmlValueTag> valueTags = new ArrayList<>();
        valueTags.add(valueTag);
        chatBot.put(keyName, valueTags);

        //parent node
        Node rootNode = document.getDocumentElement();
        Element childElement = getElement(valueTag, document);
        Element newElement = createNewKey(keyName, document, childElement);

        rootNode.appendChild(newElement);

        new Thread(() -> {
            try {
                writeToXmlFile(document);
            } catch (TransformerException e) {
                e.printStackTrace();
            }
        }).start();
    }

    private Element createNewKey(String keyName, Document document, Element childElement) {

        Element valuesElement = document.createElement("values");
        valuesElement.appendChild(childElement);

        Element newElement = document.createElement("key");
        newElement.setAttribute("name", keyName);
        newElement.appendChild(valuesElement);
        return newElement;
    }

    private Element getElement(XmlValueTag valueTag, Document document) {

        Element childElement = document.createElement("value");
        childElement.setAttribute("weight", String.valueOf(valueTag.getWeight()));
        childElement.setTextContent(valueTag.getValue());
        return childElement;
    }

    @Override
    public String getMessage(String message) {

        String respond = "sorry !! what do you mean ?";
        message = message.toLowerCase().trim();
        List<XmlValueTag> valueTags = search(message);

        if (!valueTags.isEmpty()) {
            valueTags.sort(Comparator.comparing(chatbot.XmlValueTag::getWeight).reversed());
            respond = valueTags.get(0).getValue();
        }else if(defaultMessage){
            respond = "sorry, I'm busy now. Can you call me later ?";
            defaultMessage = false;
        }else
            defaultMessage = true;
        return respond;
    }

    @Override
    public void learn(String message, String respond) {

        message = message.toLowerCase().trim();
        respond = respond.toLowerCase().trim();
        XmlValueTag valueTag = new XmlValueTag(respond);
        try {
            enableChatBotToLearn(message, valueTag);
        } catch (XPathExpressionException | TransformerException e) {
            e.printStackTrace();
        }
    }
}