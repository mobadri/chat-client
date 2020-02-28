package com.chat.client.service.client.message.impl;

import chatbot.factory.ChatBotFactory;
import com.chat.client.network.client.chat.MessageHandler;
import com.chat.client.network.client.factory.NetworkFactory;
import com.chat.client.service.client.callback.MessageServiceCallBack;
import com.chat.client.service.client.message.ClientMessageService;
import com.chat.client.service.client.message.ModelAdapter;
import com.chat.server.model.chat.Message;
import com.chat.server.model.chat.xmlmessage.MessageType;
import com.chat.server.model.chat.xmlmessage.MessagesType;
import com.chat.server.model.chat.xmlmessage.ObjectFactory;
import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.bind.*;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

public class ClientMessageServiceImpl implements ClientMessageService {

    //@yassmin
    //todo impl this class
    // using the factory object from Repository layer
    MessageHandler messageHandler = NetworkFactory.createMessageHandler();

    private static ClientMessageServiceImpl instance;

    private ClientMessageServiceImpl() {

    }

    @Override
    public void sendMessage(Message message) {
        messageHandler.sendMessage(message);
    }

    @Override
    public void register(MessageServiceCallBack messageServiceCallBack) {
        messageHandler.register(messageServiceCallBack);
    }

    @Override
    public void unRegister(MessageServiceCallBack messageServiceCallBack) {
        messageHandler.unRegister(messageServiceCallBack);
    }

    @Override
    public void saveXmlFile(List<Message> messageList, String path) {
        try {
            ModelAdapter modelAdapter = new ModelAdapter();
            JAXBContext context = JAXBContext.newInstance(ObjectFactory.class);
            // list of messages
            MessagesType messages = new MessagesType();

            for (Message message : messageList) {
                MessageType messageType = modelAdapter.generateMessageType(message);
                messages.getMessage().add(messageType);
            }
            JAXBElement<MessagesType> listOfMessagesType = new ObjectFactory().createMessages(messages);
            Marshaller marsh = context.createMarshaller();
            marsh.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            // validate
            SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            File schemaFile = new File("messageSchema.xsd");
            Schema schema = schemaFactory.newSchema(schemaFile);
            marsh.setSchema(schema);
            marsh.marshal(listOfMessagesType, new FileOutputStream(path));
        } catch (JAXBException | FileNotFoundException | SAXException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Message> loadFromXml(String path) {
        List<Message> messageList = new ArrayList<>();
        try {
            ModelAdapter modelAdapter = new ModelAdapter();
            JAXBContext context = JAXBContext.newInstance(ObjectFactory.class);
            Unmarshaller unmarsh = context.createUnmarshaller();
            JAXBElement<MessagesType> unmarshal = (JAXBElement) unmarsh.unmarshal(new File(path));
            MessagesType messages = unmarshal.getValue();
            for (MessageType messageType : messages.getMessage()) {
                messageList.add(modelAdapter.generateMessage(messageType));
            }
            return messageList;
        } catch (JAXBException e) {
            e.printStackTrace();
        }
        return messageList;

    }

    public static synchronized ClientMessageServiceImpl createMessageGroupServiceInstance() {
        if (instance == null) {
            instance = new ClientMessageServiceImpl();
        }
        return instance;
    }

}