package com.chat.client.view.client.chat.render;

import javafx.scene.image.Image;

import java.io.ByteArrayInputStream;

public class RenderImage {
    public RenderImage() {
    }

    public Image convertToImage(byte[] image) {
        if (image != null && image.length > 0) {
            return new Image(new ByteArrayInputStream(image));
        }
        return null;
    }
}