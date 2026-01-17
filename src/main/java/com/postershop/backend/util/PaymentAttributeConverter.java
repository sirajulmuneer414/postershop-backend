package com.postershop.backend.util;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import org.springframework.beans.factory.annotation.Value;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.util.Base64;

    // This converter encrypts and decrypts payment-related attributes using a specified algorithm and secret key.

@Converter
public class PaymentAttributeConverter implements AttributeConverter<String, String> {

    private static String ALGORITHM;
    private static String SECRET_KEY;
    // Setter injection for static fields
    @Value("${app.encryption.algorithm}")
    public void setAlgorithm(String algorithm) {
        ALGORITHM = algorithm;
    }

    @Value("${app.encryption.secret-key}")
    public void setKey(String key) {
        SECRET_KEY = key;
        // Note: Make sure your key in properties is exactly 16 chars for AES-128
    }


    @Override
    public String convertToDatabaseColumn(String attribute) {
        if(attribute == null) return null;

        try {
            Key key = new SecretKeySpec(SECRET_KEY.getBytes(), ALGORITHM);
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, key);
            byte[] encryptedBytes = cipher.doFinal(attribute.getBytes());
            return Base64.getEncoder().encodeToString(encryptedBytes);
        }
        catch (Exception e) {
            throw new RuntimeException("Error occurred during encryption", e);
        }
    }

    @Override
    public String convertToEntityAttribute(String dbData) {
        if(dbData == null) return null;
        try {
            Key key = new SecretKeySpec(SECRET_KEY.getBytes(), ALGORITHM);
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, key);
            byte[] decryptedBytes = cipher.doFinal(Base64.getDecoder().decode(dbData));
            return new String(decryptedBytes);
    }
        catch (Exception e) {
            throw new RuntimeException("Error occurred during decryption", e);
        }
    }
}
