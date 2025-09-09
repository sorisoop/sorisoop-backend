package com.futurenet.sorisoopbackend.global.util;

import com.futurenet.sorisoopbackend.global.exception.GlobalErrorCode;
import com.futurenet.sorisoopbackend.global.exception.RestApiException;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.util.Base64;

public class ImageUtil {
    public static String downloadImageAsBase64(String imageUrl) {
        try (InputStream inputStream = URI.create(imageUrl).toURL().openStream()) {
            byte[] bytes = inputStream.readAllBytes();
            return Base64.getEncoder().encodeToString(bytes);
        } catch (IOException e) {
            throw new RestApiException(GlobalErrorCode.INVALID_URL);
        }
    }
}
