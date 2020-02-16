package chat_bot.impl;

import chat_bot.ChatBotInterface;
import chat_bot.XmlValueTag;
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
    private Map<String, List<XmlValueTag>> chatBot = new TreeMap<>();

    public static ChatBot getInstance() throws IOException, SAXException, ParserConfigurationException {

        if(chatBotInstance == null)
            chatBotInstance = new ChatBot();
        return chatBotInstance;
    }

    private ChatBot() throws ParserConfigurationException, SAXException, IOException {

        xmlFile = new File("chat_bot_keys.xml");
        readXml(xmlFile);
    }

    public void readXml(File xmlFile) throws IOException, SAXException, ParserConfigurationException {

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = factory.newDocumentBuilder();
        document = dBuilder.parse(xmlFile);

        NodeList keys = document.getElementsByTagName("key");
        List<XmlValueTag> xmlValueTagList;

        for (int i = 0; i < keys.getLength(); i++) {

            Node nNode = keys.item(i);
            if (nNode.getNodeType() == Node.ELEMENT_NODE) {

                Element elem = (Element) nNode;
                String name = elem.getAttribute("name");

                NodeList values = elem.getElementsByTagName("value");
                xmlValueTagList = new ArrayList<>();

                for (int j = 0; j < values.getLength(); j++) {

                    Node value = values.item(j);
                    if (value.getNodeType() == Node.ELEMENT_NODE) {

                        Element element = (Element) value;
                        xmlValueTagList.add(new XmlValueTag(element.getTextContent(),
                                Integer.parseInt(element.getAttribute("weight"))));
                    }
                }
                chatBot.put(name, xmlValueTagList);
            }
        }
    }

    public List<XmlValueTag> search(String key) {

        List<XmlValueTag> xmlValueTagList = new ArrayList<>();
        chatBot.keySet().parallelStream().filter((elem) -> elem.contains(key))
                .forEach((elem) -> xmlValueTagList.addAll(chatBot.get(elem)));
        return xmlValueTagList;
    }

    public void enableChatBotToLearn(String keyName, XmlValueTag valueTag) throws
            ParserConfigurationException, IOException, SAXException, XPathExpressionException, TransformerException {

        XPathFactory factory = XPathFactory.newInstance();
        XPath path = factory.newXPath();

        String valueXpath = "/keys/key[contains(@name,'" + keyName + "')]/*/value[text() = '" + valueTag.getValue() + "']";
        XPathExpression expression = path.compile(valueXpath);
        Node keyValue = (Node) expression.evaluate(document, XPathConstants.NODE);

        if (keyValue != null) {

            chatBot.get(keyName).parallelStream().filter((value) -> value.getValue().trim().equalsIgnoreCase(valueTag.getValue()))
                    .forEach(XmlValueTag::incrementWeight);
            Node weight = keyValue.getAttributes().getNamedItem("weight");
            weight.setNodeValue(String.valueOf(Integer.parseInt(weight.getNodeValue()) + 1));
        } else {

            String expr = "/keys/key[contains(@name,'" + keyName + "')]/values";
            expression = path.compile(expr);

            Node keyValues = (Node) expression.evaluate(document, XPathConstants.NODE);
            if (keyValues != null) {

                chatBot.get(keyName).add(valueTag);
                Element newValue = getElement(valueTag, document);
                keyValues.appendChild(newValue);
            } else {
                addNewKey(keyName, valueTag);
            }
        }
        writeToXmlFile(document);
    }

    private void writeToXmlFile(Document doc) throws TransformerException {

        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        DOMSource source = new DOMSource(doc);
        StreamResult result = new StreamResult(xmlFile);
        transformer.setOutputProperty(OutputKeys.METHOD, "xml");
        transformer.transform(source, result);
    }

    public void addNewKey(String keyName, XmlValueTag valueTag)
            throws ParserConfigurationException, IOException, SAXException, TransformerException {

        List<XmlValueTag> valueTags = new ArrayList<>();
        valueTags.add(valueTag);
        chatBot.put(keyName, valueTags);

        //parent node
        Node rootNode = document.getDocumentElement();
        Element childElement = getElement(valueTag, document);
        Element newElement = createNewKey(keyName, document, childElement);

        rootNode.appendChild(newElement);
        writeToXmlFile(document);
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
        message = message.toLowerCase();
        List<XmlValueTag> valueTags = search(message);
        System.out.println(valueTags);
        if (!valueTags.isEmpty()) {
            valueTags.sort(Comparator.comparing(chat_bot.XmlValueTag::getWeight).reversed());
            respond = valueTags.get(0).getValue();
        }
        return respond;
    }

    @Override
    public void learn(String message, String respond) {

        XmlValueTag valueTag = new XmlValueTag(respond, 1);
        try {
            enableChatBotToLearn(message, valueTag);
        } catch (ParserConfigurationException | IOException | SAXException | XPathExpressionException | TransformerException e) {
            e.printStackTrace();
        }
    }
}