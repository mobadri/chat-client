package com.chat.client.view.client.chat;

import javafx.scene.image.Image;

import java.io.ByteArrayInputStream;

public class RenderImage {
    public RenderImage() {
    }

    public Image convertToImage(byte[] image, int width,
                                int height, boolean preserveRatio, boolean smooth) {
        if (image != null && image.length > 0) {
            return new Image(new ByteArrayInputStream(image), width, height, preserveRatio, smooth);
        }
        return null;
    }
}