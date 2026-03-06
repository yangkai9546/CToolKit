package com.wonders.ctoolkit.utils;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.MessageDigest;
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
        SecretKeySpec secretKey = deriveAESKey(key);
        Cipher cipher = iv != null && !iv.isEmpty() ?
            Cipher.getInstance(AES_CBC_TRANSFORMATION) :
            Cipher.getInstance(AES_TRANSFORMATION);

        if (iv != null && !iv.isEmpty()) {
            IvParameterSpec ivSpec = deriveAESIV(iv);
            cipher.init(Cipher.ENCRYPT_MODE, secretKey, ivSpec);
        } else {
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
        }

        byte[] encryptedBytes = cipher.doFinal(plainText.getBytes(StandardCharsets.UTF_8));
        return Base64.getEncoder().encodeToString(encryptedBytes);
    }

    public static String decryptAES(String encryptedText, String key) throws Exception {
        return decryptAES(encryptedText, key, null);
    }

    public static String decryptAES(String encryptedText, String key, String iv) throws Exception {
        SecretKeySpec secretKey = deriveAESKey(key);
        Cipher cipher = iv != null && !iv.isEmpty() ?
            Cipher.getInstance(AES_CBC_TRANSFORMATION) :
            Cipher.getInstance(AES_TRANSFORMATION);

        if (iv != null && !iv.isEmpty()) {
            IvParameterSpec ivSpec = deriveAESIV(iv);
            cipher.init(Cipher.DECRYPT_MODE, secretKey, ivSpec);
        } else {
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
        }

        byte[] decryptedBytes = cipher.doFinal(Base64.getDecoder().decode(encryptedText));
        return new String(decryptedBytes, StandardCharsets.UTF_8);
    }

    // AES Encryption/Decryption with Hex encoding
    public static String encryptAESHex(String plainText, String key) throws Exception {
        return encryptAESHex(plainText, key, null);
    }

    public static String encryptAESHex(String plainText, String key, String iv) throws Exception {
        SecretKeySpec secretKey = deriveAESKey(key);
        Cipher cipher = iv != null && !iv.isEmpty() ?
            Cipher.getInstance(AES_CBC_TRANSFORMATION) :
            Cipher.getInstance(AES_TRANSFORMATION);

        if (iv != null && !iv.isEmpty()) {
            IvParameterSpec ivSpec = deriveAESIV(iv);
            cipher.init(Cipher.ENCRYPT_MODE, secretKey, ivSpec);
        } else {
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
        }

        byte[] encryptedBytes = cipher.doFinal(plainText.getBytes(StandardCharsets.UTF_8));
        return bytesToHex(encryptedBytes);
    }

    public static String decryptAESHex(String encryptedText, String key) throws Exception {
        return decryptAESHex(encryptedText, key, null);
    }

    public static String decryptAESHex(String encryptedText, String key, String iv) throws Exception {
        SecretKeySpec secretKey = deriveAESKey(key);
        Cipher cipher = iv != null && !iv.isEmpty() ?
            Cipher.getInstance(AES_CBC_TRANSFORMATION) :
            Cipher.getInstance(AES_TRANSFORMATION);

        if (iv != null && !iv.isEmpty()) {
            IvParameterSpec ivSpec = deriveAESIV(iv);
            cipher.init(Cipher.DECRYPT_MODE, secretKey, ivSpec);
        } else {
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
        }

        byte[] decryptedBytes = cipher.doFinal(hexToBytes(encryptedText));
        return new String(decryptedBytes, StandardCharsets.UTF_8);
    }

    // AES Encryption/Decryption with mode parameter
    public static String encryptAES(String plainText, String key, String iv, String mode) throws Exception {
        SecretKeySpec secretKey = deriveAESKey(key);
        String transformation = "AES/" + mode;
        Cipher cipher = Cipher.getInstance(transformation);

        if (mode.startsWith("CBC")) {
            if (iv != null && !iv.isEmpty()) {
                IvParameterSpec ivSpec = deriveAESIV(iv);
                cipher.init(Cipher.ENCRYPT_MODE, secretKey, ivSpec);
            } else {
                throw new IllegalArgumentException("CBC mode requires an IV");
            }
        } else {
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
        }

        byte[] encryptedBytes = cipher.doFinal(plainText.getBytes(StandardCharsets.UTF_8));
        return Base64.getEncoder().encodeToString(encryptedBytes);
    }

    public static String decryptAES(String encryptedText, String key, String iv, String mode) throws Exception {
        SecretKeySpec secretKey = deriveAESKey(key);
        String transformation = "AES/" + mode;
        Cipher cipher = Cipher.getInstance(transformation);

        if (mode.startsWith("CBC")) {
            if (iv != null && !iv.isEmpty()) {
                IvParameterSpec ivSpec = deriveAESIV(iv);
                cipher.init(Cipher.DECRYPT_MODE, secretKey, ivSpec);
            } else {
                throw new IllegalArgumentException("CBC mode requires an IV");
            }
        } else {
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
        }

        byte[] decryptedBytes = cipher.doFinal(Base64.getDecoder().decode(encryptedText));
        return new String(decryptedBytes, StandardCharsets.UTF_8);
    }

    // AES Encryption/Decryption with mode and Hex encoding
    public static String encryptAESHex(String plainText, String key, String iv, String mode) throws Exception {
        SecretKeySpec secretKey = deriveAESKey(key);
        String transformation = "AES/" + mode;
        Cipher cipher = Cipher.getInstance(transformation);

        if (mode.startsWith("CBC")) {
            if (iv != null && !iv.isEmpty()) {
                IvParameterSpec ivSpec = deriveAESIV(iv);
                cipher.init(Cipher.ENCRYPT_MODE, secretKey, ivSpec);
            } else {
                throw new IllegalArgumentException("CBC mode requires an IV");
            }
        } else {
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
        }

        byte[] encryptedBytes = cipher.doFinal(plainText.getBytes(StandardCharsets.UTF_8));
        return bytesToHex(encryptedBytes);
    }

    public static String decryptAESHex(String encryptedText, String key, String iv, String mode) throws Exception {
        SecretKeySpec secretKey = deriveAESKey(key);
        String transformation = "AES/" + mode;
        Cipher cipher = Cipher.getInstance(transformation);

        if (mode.startsWith("CBC")) {
            if (iv != null && !iv.isEmpty()) {
                IvParameterSpec ivSpec = deriveAESIV(iv);
                cipher.init(Cipher.DECRYPT_MODE, secretKey, ivSpec);
            } else {
                throw new IllegalArgumentException("CBC mode requires an IV");
            }
        } else {
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
        }

        byte[] decryptedBytes = cipher.doFinal(hexToBytes(encryptedText));
        return new String(decryptedBytes, StandardCharsets.UTF_8);
    }

    /**
     * Creates an AES key from a string by using its UTF-8 bytes directly.
     * Supports key lengths of 16, 24, or 32 bytes (AES-128, AES-192, AES-256).
     * If the key length is invalid, it will be padded or truncated to 16 bytes.
     *
     * @param keyString The user-provided key string
     * @return A SecretKeySpec suitable for AES encryption
     */
    private static SecretKeySpec deriveAESKey(String keyString) throws Exception {
        byte[] keyBytes = keyString.getBytes(StandardCharsets.UTF_8);
        int keyLength = keyBytes.length;

        // Determine the appropriate key size
        int targetSize;
        if (keyLength >= 32) {
            targetSize = 32;
        } else if (keyLength >= 24) {
            targetSize = 24;
        } else {
            targetSize = 16;
        }

        // Pad or truncate to target size
        byte[] finalKeyBytes = new byte[targetSize];
        System.arraycopy(keyBytes, 0, finalKeyBytes, 0, Math.min(keyLength, targetSize));
        return new SecretKeySpec(finalKeyBytes, AES_ALGORITHM);
    }

    /**
     * Creates an AES IV from a string by using its UTF-8 bytes directly.
     * AES CBC mode requires the IV to be exactly 16 bytes.
     * If the string is shorter than 16 bytes, it will be padded with zeros.
     * If longer, it will be truncated to 16 bytes.
     *
     * @param ivString The user-provided IV string
     * @return An IvParameterSpec suitable for AES CBC mode
     */
    private static IvParameterSpec deriveAESIV(String ivString) throws Exception {
        byte[] ivBytes = ivString.getBytes(StandardCharsets.UTF_8);
        byte[] ivBytes16 = new byte[16];
        System.arraycopy(ivBytes, 0, ivBytes16, 0, Math.min(ivBytes.length, 16));
        return new IvParameterSpec(ivBytes16);
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

    // RSA Encryption/Decryption with Hex encoding
    public static String encryptRSAHex(String plainText, String publicKey) throws Exception {
        byte[] publicKeyBytes = Base64.getDecoder().decode(publicKey);
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(publicKeyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(RSA_ALGORITHM);
        PublicKey pubKey = keyFactory.generatePublic(keySpec);

        Cipher cipher = Cipher.getInstance(RSA_TRANSFORMATION);
        cipher.init(Cipher.ENCRYPT_MODE, pubKey);
        byte[] encryptedBytes = cipher.doFinal(plainText.getBytes());
        return bytesToHex(encryptedBytes);
    }

    public static String decryptRSAHex(String encryptedText, String privateKey) throws Exception {
        byte[] privateKeyBytes = Base64.getDecoder().decode(privateKey);
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(privateKeyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(RSA_ALGORITHM);
        PrivateKey privKey = keyFactory.generatePrivate(keySpec);

        Cipher cipher = Cipher.getInstance(RSA_TRANSFORMATION);
        cipher.init(Cipher.DECRYPT_MODE, privKey);
        byte[] decryptedBytes = cipher.doFinal(hexToBytes(encryptedText));
        return new String(decryptedBytes);
    }

    // Key Generation
    /**
     * Generates a random 32-character string suitable for use as an AES key.
     * The actual encryption key is derived from this string using SHA-256.
     *
     * @return A random 32-character alphanumeric string
     */
    public static String generateAESKey() throws Exception {
        // Generate a 32-character random string (user-friendly)
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder key = new StringBuilder(32);
        java.util.Random random = new java.security.SecureRandom();
        for (int i = 0; i < 32; i++) {
            key.append(chars.charAt(random.nextInt(chars.length())));
        }
        return key.toString();
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

    // Hex encoding/decoding helper methods
    /**
     * Converts a byte array to a hexadecimal string.
     *
     * @param bytes The byte array to convert
     * @return A hexadecimal string representation
     */
    private static String bytesToHex(byte[] bytes) {
        StringBuilder hexString = new StringBuilder(bytes.length * 2);
        for (byte b : bytes) {
            String hex = Integer.toHexString(0xff & b);
            if (hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }
        return hexString.toString();
    }

    /**
     * Converts a hexadecimal string to a byte array.
     *
     * @param hex The hexadecimal string to convert
     * @return A byte array
     */
    private static byte[] hexToBytes(String hex) {
        int len = hex.length();
        byte[] data = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte) ((Character.digit(hex.charAt(i), 16) << 4)
                    + Character.digit(hex.charAt(i + 1), 16));
        }
        return data;
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