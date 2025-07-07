package com.intcomex.productapi.infrastructure.utils;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public class ImageUtils {

    private ImageUtils() {
    }

    public static byte[] toByteArray(MultipartFile file) {
        try {
            if (file == null || file.isEmpty()) {
                return null;
            }
            return file.getBytes();
        } catch (IOException e) {
            throw new RuntimeException("Error al convertir la imagen a byte[]", e);
        }
    }
}

