package com.chat.client.controller.client.message;

import com.chat.server.model.chat.ChatGroup;
import com.chat.server.model.chat.Message;
import com.chat.server.model.chat.Style;
import com.chat.server.model.chat.xmlmessage.*;
import com.chat.server.model.user.User;

import javax.xml.bind.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

public class MessageControllerImpl implements MessageController {
    List<Message> messageList;

    public MessageControllerImpl() {
        messageList = new ArrayList<>();
    }

    @Override
    public void saveMessages(List<Message> messageList, String path) {
        try {
            JAXBContext context = JAXBContext.newInstance(ObjectFactory.class);
            // list of messages
            MessagesType messages = new MessagesType();

            for (Message message : messageList) {
                MessageType messageType = generateMessageType(message);
                messages.getMessage().add(messageType);
            }
            JAXBElement<MessagesType> jaxbElement = new ObjectFactory().createMessages(messages);
            Marshaller marsh = context.createMarshaller();
            marsh.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            marsh.marshal(messages, new FileOutputStream("message.xml"));


//            SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
//            Schema schema = schemaFactory.newSchema(new File(this.getClass().getResource("/templates/messageSchema.xsd").toString()));
//            marsh.setSchema(schema);
            marsh.marshal(jaxbElement, new FileOutputStream(path));
        } catch (JAXBException | FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private MessageType generateMessageType(Message message) {
        ChatGroupType chatGroupType = generateChatGroupType(message.getChatGroup());
        UserFromType userFromType = generateUserFromType(message.getUserFrom());
        StyleType styleType = generateStyleType(message.getStyle());
        MessageType messageType = new MessageType();
        messageType.setId("" + message.getId());
        messageType.setMessageContent(message.getMessage());
        messageType.setChatGroup(chatGroupType);
        messageType.setUserFrom(userFromType);
        messageType.setStyle(styleType);
        return messageType;
    }

    private StyleType generateStyleType(Style style) {
        StyleType styleType = new StyleType();
        styleType.setFillBackground(style.getBackground());
        styleType.setFontColor(style.getFontColor());
        styleType.setFontFamily(style.getFontFamily());
        styleType.setFontName(style.getFontName());
        styleType.setFontSize("" + style.getFontSize());
        styleType.setFontWeight((style.isBold() ? "bold" : "") + (style.isItalic() ? "italic" : ""));
        return styleType;
    }

    private UserFromType generateUserFromType(User userFrom) {
        UserFromType userFromType = new UserFromType();
        userFromType.setId("" + userFrom.getId());
        userFromType.setFirstName(userFrom.getFirstName());
        userFromType.setLastName(userFrom.getLastName());
        userFromType.setPhone(userFrom.getPhone());
        return userFromType;
    }

    private ChatGroupType generateChatGroupType(ChatGroup chatGroup) {
        ChatGroupType chatGroupType = new ChatGroupType();
        chatGroupType.setId("" + chatGroup.getId());
        chatGroupType.setName(chatGroup.getName());
        return chatGroupType;
    }

    @Override
    public List<Message> loadMessages(String path) {
        List<Message> messageList = new ArrayList<>();
        try {
            JAXBContext context = JAXBContext.newInstance(ObjectFactory.class);
            Unmarshaller unmarsh = context.createUnmarshaller();
            JAXBElement<MessagesType> unmarshal = (JAXBElement) unmarsh.unmarshal(new File(path));
            MessagesType messages = unmarshal.getValue();
            for (MessageType messageType : messages.getMessage()) {
                messageList.add(generateMessage(messageType));
            }
            return messageList;
        } catch (JAXBException e) {
            e.printStackTrace();
        }
        return messageList;

    }

    private Message generateMessage(MessageType messageType) {
        Style style = generateStyle(messageType.getStyle());
        ChatGroup chatGroup = generateChatGroup(messageType.getChatGroup());
        User userFrom = generateUserFrom(messageType.getUserFrom());
        Message message = new Message();
        message.setId(Integer.parseInt(messageType.getId()));
        message.setMessage(messageType.getMessageContent());
        message.setStyle(style);
        message.setChatGroup(chatGroup);
        message.setUserFrom(userFrom);
        return message;
    }

    private User generateUserFrom(UserFromType userFromType) {
        User user = new User();
        user.setId(Integer.parseInt(userFromType.getId()));
        user.setFirstName(userFromType.getFirstName());
        user.setLastName(userFromType.getLastName());
        user.setPhone(userFromType.getPhone());
        return user;
    }

    private ChatGroup generateChatGroup(ChatGroupType chatGroupType) {
        ChatGroup chatGroup = new ChatGroup();
        chatGroup.setId(Integer.parseInt(chatGroupType.getId()));
        chatGroup.setName(chatGroupType.getName());
        return chatGroup;
    }

    private Style generateStyle(StyleType styleType) {
        Style style = new Style();
        style.setFontColor(styleType.getFontColor());
        style.setBackground(styleType.getFillBackground());
        style.setFontName(styleType.getFontName());
        style.setFontSize(Integer.parseInt(styleType.getFontSize()));
        style.setFontFamily(styleType.getFontFamily());
        style.setBold(styleType.getFontWeight().contains("bold"));
        style.setItalic(styleType.getFontWeight().contains("italic"));
        return style;
    }
}
