package com.chat.client.service.client.message;

import com.chat.server.model.chat.ChatGroup;
import com.chat.server.model.chat.Message;
import com.chat.server.model.chat.Style;
import com.chat.server.model.chat.xmlmessage.ChatGroupType;
import com.chat.server.model.chat.xmlmessage.MessageType;
import com.chat.server.model.chat.xmlmessage.StyleType;
import com.chat.server.model.chat.xmlmessage.UserFromType;
import com.chat.server.model.user.User;

public class ModelAdapter {
    public ModelAdapter() {
    }

    public MessageType generateMessageType(Message message) {
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

    public Message generateMessage(MessageType messageType) {
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
