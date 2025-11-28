package com.wonders.ctoolkit.utils;

import org.junit.Test;
import static org.junit.Assert.*;

public class CryptoUtilsTest {
    
    @Test
    public void testAESGenerateKey() {
        try {
            String key = CryptoUtils.generateAESKey();
            assertNotNull("Generated key should not be null", key);
            assertFalse("Generated key should not be empty", key.isEmpty());
        } catch (Exception e) {
            fail("Exception occurred while generating AES key: " + e.getMessage());
        }
    }
    
    @Test
    public void testAESEncryptionDecryption() {
        try {
            String originalText = "Hello, World!";
            String key = CryptoUtils.generateAESKey();
            
            String encrypted = CryptoUtils.encryptAES(originalText, key);
            assertNotNull("Encrypted text should not be null", encrypted);
            assertFalse("Encrypted text should not be empty", encrypted.isEmpty());
            
            String decrypted = CryptoUtils.decryptAES(encrypted, key);
            assertEquals("Decrypted text should match original", originalText, decrypted);
        } catch (Exception e) {
            fail("Exception occurred during AES encryption/decryption: " + e.getMessage());
        }
    }
    
    @Test
    public void testRSAKeyPairGeneration() {
        try {
            java.security.KeyPair keyPair = CryptoUtils.generateRSAKeyPair();
            assertNotNull("KeyPair should not be null", keyPair);
            assertNotNull("Public key should not be null", keyPair.getPublic());
            assertNotNull("Private key should not be null", keyPair.getPrivate());
        } catch (Exception e) {
            fail("Exception occurred while generating RSA key pair: " + e.getMessage());
        }
    }
}