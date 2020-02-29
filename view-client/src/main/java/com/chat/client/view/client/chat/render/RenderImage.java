package com.chat.client.view.client.chat.render;

import javafx.scene.image.Image;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class RenderImage {
    public RenderImage() {
    }

    public Image convertToImage(byte[] image) {
        if (image != null && image.length > 0) {
            return new Image(new ByteArrayInputStream(image));
        }
        return null;
    }

    public byte[] convertToByte(String path) {
        byte[] image = null;
        try {
            image = Files.readAllBytes(Paths.get(path));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return image;
    }
}