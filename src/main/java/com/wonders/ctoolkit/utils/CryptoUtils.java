package com.wonders.ctoolkit.utils;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

public class CryptoUtils {
    
    // AES constants
    private static final String AES_ALGORITHM = "AES";
    private static final String AES_TRANSFORMATION = "AES/ECB/PKCS5Padding";
    private static final String AES_CBC_TRANSFORMATION = "AES/CBC/PKCS5Padding";
    
    // RSA constants
    private static final String RSA_ALGORITHM = "RSA";
    private static final String RSA_TRANSFORMATION = "RSA/ECB/PKCS1Padding";
    
    // SM4 constants (Note: SM4 is not part of standard Java crypto, 
    // this is a placeholder - in a real implementation you would use a third-party library like Bouncy Castle)
    private static final String SM4_ALGORITHM = "SM4";
    private static final String SM4_TRANSFORMATION = "SM4/ECB/PKCS5Padding";
    private static final String SM4_CBC_TRANSFORMATION = "SM4/CBC/PKCS5Padding";
    
    // SM2 constants (Note: SM2 is not part of standard Java crypto,
    // this is a placeholder - in a real implementation you would use a third-party library like Bouncy Castle)
    private static final String SM2_ALGORITHM = "SM2";
    private static final String SM2_TRANSFORMATION = "SM2";

    // AES Encryption/Decryption
    public static String encryptAES(String plainText, String key) throws Exception {
        return encryptAES(plainText, key, null);
    }
    
    public static String encryptAES(String plainText, String key, String iv) throws Exception {
        SecretKeySpec secretKey = new SecretKeySpec(Base64.getDecoder().decode(key), AES_ALGORITHM);
        Cipher cipher = iv != null && !iv.isEmpty() ? 
            Cipher.getInstance(AES_CBC_TRANSFORMATION) : 
            Cipher.getInstance(AES_TRANSFORMATION);
        
        if (iv != null && !iv.isEmpty()) {
            IvParameterSpec ivSpec = new IvParameterSpec(iv.getBytes());
            cipher.init(Cipher.ENCRYPT_MODE, secretKey, ivSpec);
        } else {
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
        }
        
        byte[] encryptedBytes = cipher.doFinal(plainText.getBytes());
        return Base64.getEncoder().encodeToString(encryptedBytes);
    }

    public static String decryptAES(String encryptedText, String key) throws Exception {
        return decryptAES(encryptedText, key, null);
    }
    
    public static String decryptAES(String encryptedText, String key, String iv) throws Exception {
        SecretKeySpec secretKey = new SecretKeySpec(Base64.getDecoder().decode(key), AES_ALGORITHM);
        Cipher cipher = iv != null && !iv.isEmpty() ? 
            Cipher.getInstance(AES_CBC_TRANSFORMATION) : 
            Cipher.getInstance(AES_TRANSFORMATION);
        
        if (iv != null && !iv.isEmpty()) {
            IvParameterSpec ivSpec = new IvParameterSpec(iv.getBytes());
            cipher.init(Cipher.DECRYPT_MODE, secretKey, ivSpec);
        } else {
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
        }
        
        byte[] decryptedBytes = cipher.doFinal(Base64.getDecoder().decode(encryptedText));
        return new String(decryptedBytes);
    }

    // RSA Encryption/Decryption
    public static String encryptRSA(String plainText, String publicKey) throws Exception {
        byte[] publicKeyBytes = Base64.getDecoder().decode(publicKey);
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(publicKeyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(RSA_ALGORITHM);
        PublicKey pubKey = keyFactory.generatePublic(keySpec);
        
        Cipher cipher = Cipher.getInstance(RSA_TRANSFORMATION);
        cipher.init(Cipher.ENCRYPT_MODE, pubKey);
        byte[] encryptedBytes = cipher.doFinal(plainText.getBytes());
        return Base64.getEncoder().encodeToString(encryptedBytes);
    }

    public static String decryptRSA(String encryptedText, String privateKey) throws Exception {
        byte[] privateKeyBytes = Base64.getDecoder().decode(privateKey);
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(privateKeyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(RSA_ALGORITHM);
        PrivateKey privKey = keyFactory.generatePrivate(keySpec);
        
        Cipher cipher = Cipher.getInstance(RSA_TRANSFORMATION);
        cipher.init(Cipher.DECRYPT_MODE, privKey);
        byte[] decryptedBytes = cipher.doFinal(Base64.getDecoder().decode(encryptedText));
        return new String(decryptedBytes);
    }

    // Key Generation
    public static String generateAESKey() throws Exception {
        KeyGenerator keyGenerator = KeyGenerator.getInstance(AES_ALGORITHM);
        keyGenerator.init(128);
        SecretKey secretKey = keyGenerator.generateKey();
        return Base64.getEncoder().encodeToString(secretKey.getEncoded());
    }
    
    public static KeyPair generateRSAKeyPair() throws Exception {
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(RSA_ALGORITHM);
        keyPairGenerator.initialize(2048);
        return keyPairGenerator.generateKeyPair();
    }
    
    public static String getPublicKeyString(PublicKey publicKey) {
        return Base64.getEncoder().encodeToString(publicKey.getEncoded());
    }
    
    public static String getPrivateKeyString(PrivateKey privateKey) {
        return Base64.getEncoder().encodeToString(privateKey.getEncoded());
    }
    
    // Placeholder methods for SM4 and SM2 (would require Bouncy Castle or similar library)
    public static String encryptSM4(String plainText, String key) throws Exception {
        throw new UnsupportedOperationException("SM4 encryption requires a third-party library like Bouncy Castle");
    }
    
    public static String decryptSM4(String encryptedText, String key) throws Exception {
        throw new UnsupportedOperationException("SM4 decryption requires a third-party library like Bouncy Castle");
    }
    
    public static String encryptSM2(String plainText, String publicKey) throws Exception {
        throw new UnsupportedOperationException("SM2 encryption requires a third-party library like Bouncy Castle");
    }
    
    public static String decryptSM2(String encryptedText, String privateKey) throws Exception {
        throw new UnsupportedOperationException("SM2 decryption requires a third-party library like Bouncy Castle");
    }
}