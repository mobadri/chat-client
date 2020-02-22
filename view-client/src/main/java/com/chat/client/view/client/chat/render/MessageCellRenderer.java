package com.chat.client.view.client.chat.render;

import com.chat.server.model.chat.Message;
import javafx.geometry.Pos;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.util.Callback;

public class MessageCellRenderer implements Callback<ListView<Message>, ListCell<Message>> {
    private RenderImage renderImage = new RenderImage();

    @Override
    public ListCell<Message> call(ListView<Message> messageListView) {

        ListCell<Message> cell = new ListCell<Message>() {

            @Override
            protected void updateItem(Message message, boolean bln) {
                super.updateItem(message, bln);
                setGraphic(null);
                setText(null);
                Circle circle = new Circle();
                if (message != null) {
                    HBox hBox = new HBox();
                    Text messageText = new Text(message.getMessage());
                    messageText.wrappingWidthProperty().set(getWidth() - 120);
                    StackPane pane = new StackPane();
                    pane.getChildren().add(messageText);
                    pane.setStyle(" -fx-background-radius: 10px;" + message.getStyle().toString());
                    pane.setAlignment(Pos.BASELINE_LEFT);
                    Image image = renderImage.convertToImage(message.getUserFrom().getImage());
                    if (image == null) {
                        image = new Image(getClass().getResource("/static/images/Smile.png").toString(), 40, 40, true, false);
                    }
                    circle.setCenterX(40);
                    circle.setRadius(40);

                    StackPane stackPane = new StackPane();
                    circle.setFill(new ImagePattern(image));
                    stackPane.getChildren().addAll(circle);

                    hBox.setSpacing(5);
                    hBox.setPrefWidth(350);
                    hBox.getChildren().addAll(stackPane, pane);
                    hBox.setAlignment(Pos.CENTER_LEFT);

                    StackPane root = new StackPane();
                    root.getChildren().add(hBox);
                    root.setAlignment(hBox, Pos.CENTER);

                    setPrefHeight(root.getPrefHeight());
                    setGraphic(root);
                }
            }
        };
        return cell;
    }
}
