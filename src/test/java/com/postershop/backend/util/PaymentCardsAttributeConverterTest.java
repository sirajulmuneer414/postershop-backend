package com.postershop.backend.util;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class PaymentCardsAttributeConverterTest {


    @Test
    void testEncryptionDecryption() {

        PaymentAttributeConverter converter = new PaymentAttributeConverter();

        converter.setAlgorithm("AES");
        converter.setKey("TestSecretKey123");

        String originalCardNumber = "1234-5678-9012-3456";

        String encrypted = converter.convertToDatabaseColumn(originalCardNumber);
        String decrypted = converter.convertToEntityAttribute(encrypted);

        Assertions.assertNotNull(encrypted);
        Assertions.assertNotEquals(originalCardNumber, encrypted, "Encrypted value should differ from original");
        Assertions.assertEquals(originalCardNumber, decrypted, "Decrypted value should match the original");


    }

}
